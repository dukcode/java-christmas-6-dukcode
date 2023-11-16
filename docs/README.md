# 구현 내용 정리

## 구현 시 중점을 둔 부분

구현 시 중점을 둔 부분은 다음과 같다.

- 입력 예외 처리를 어떻게 추상화 할 수 있을지 고민했다.
    - 공통된 예외 처리 전략을 통해 중복 코드를 줄이고 일관된 예외 처리 전략을 사용할 수 있게 되었다.
- 검증의 책임이 어디에 존재해야 하는지 고민했다.
    - 검증의 종류를 두가지로 분리하고 각자의 위치로 책임을 분리했다.
- 이벤트를 일관되게 적용할 수 있도록 추상화했다.
    - 이벤트 적용 조건과 이벤트 적용 규칙을 분리해 다양한 이벤트를 생성할 수 있는 유연성을 확보했다.
    - 이벤트 적용 규칙을 인터페이스로 분리해 새로운 이벤트 적용 규칙를 쉽게 추가할 수 있게 했다.
- 패키지간의 의존성을 생각하며 패키지 구조를 짰다.
    - 어플리케이션 레이어가 다른 레이어에 의존하지 않도록 해 도메인 레이어를 재사용할 수 있도록 했다.
    - 패키지 구조 변경을 통해 프로젝트 전체의 의존성을 줄이는 방향으로 설계했다.

### 예외 처리

해당 프로젝트의 요구사항에서는 사용자가 잘못된 값을 입력할 경우 에러 메세지를 출력하고 입력이 정상일 때 까지 다시 입력을 받는다. 첫 프로젝트에서는 입력으로 인한 예외가 발생했을 때 프로그램을 종료했다.

이처럼 예외의 처리 방식은 요구사항마다 다르다. 따라서 이 예외 처리 전략을 다음과 같이 인터페이스로 추상화했다.

```java
public interface ExceptionHandler {

    Object handle(InputView inputView, OutputView outputView, Function<InputView, Object> function);

}
```

`Function` 함수형 인터페이스를 사용해 동작을 정의하고 파라미터로 `LottoGameView`를 받고 있다. 이번 프로젝트는 예외가 발생하지 않을때까지 입력을 무한정으로 다시 받는다.
따라서 `InfiniteRetryExceptionHandler`를 다음과 같이 구현할 수 있다.

```java
public class InfiniteRetryExceptionHandler implements ExceptionHandler {

    @Override
    public Object handle(InputView inputView, OutputView outputView, Function<InputView, Object> function) {
        while (true) {
            try {
                return function.apply(inputView);
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }

}
```

`while`문을 돌면서 `funtion.apply()`에서 예외가 발생하지 않으면 값을 반환하고 예외가 발생하면 예외 메세지를 출력하고 다시 `funtion.apply()`를 실행한다.

이번 프로젝트에서는 입력을 2번 받는다. 2번 모두 예외가 발생할 수 있고 입력이 모두 정살일 때까지 다시 입력을 받는 예외 처리전략을 사용한다. 따라서 `PromotionContoller`에서
생성자로 `ExceptionHandler`를 주입받고 다음과 같이 적용할 수 있다.

```java
public class PromotionController {

    // 생성자를 통해 ExceptionHandler 주입
    // 생략 ...

    private Order inputOrder() {
        return (Order) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            OrderCreateRequest orderCreateRequest = inputView.inputMenuOrderRequest();
            return reservationService.createOrder(orderCreateRequest.toOrderCreate());
        });
    }

    private ReservationDate inputReservationDate() {
        return (ReservationDate) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            ReservationDateCreateRequest reservationDateCreateRequest = inputView.inputReservationDate();
            return new ReservationDate(reservationDateCreateRequest.getReservationDate());
        });
    }

    // 생략 ...

}
```

만약 요구사항이 `입력을 최대 n번까지만 받는다`라고 변경된다면 `ExceptionHandler`를 다음과 같이 구현해서 `PromotionController`에 주입하면 일관된 입력 예외 처리가 가능하다.

```java

public class RetryExceptionHandler implements ExceptionHandler {

    private final int retryCount;

    public RetryExceptionHandler(int retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public Object handle(InputView inputView, OutputView outputView, Function<InputView, Object> function) {
        for (int i = 0; i < retryCount; ++i) {
            try {
                return function.apply(inputView);
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }
}
```

위처럼 함수형 인터페이스와 의존성 주입 활용으로 코드 변경에는 닫혀있고 기능 확장에는 열려있는 클래스를 만들 수 있게 되었다.

### 검증의 책임은 어디에 존재하는가?

어떤 인스턴스가 존재한다면 우리는 그 인스턴스가 검증된 인스턴스라고 믿고 사용한다. 즉, 검증되지 않는 객체는 생성하면 안된다. 따라서 객체의 생성자에 검증의 책임을 부여하는 것이 맞다고 생각한다.

그렇다면 검증의 책임을 DTO의 생성자에 몰아 넣는것이 맞을까? 나는 여기서 검증을 2가지로 분리할 수 있다고 생각했다.

- 비즈니스 로직과 관련된 검증
- 비즈니스 로직과 관련되지 않은 검증

해당 프로젝트에서 비즈니스 로직과 관련된 검증이란 다음과 같은 것이 있을 것이다.

- 주문은 음료로만 이루어지면 안된다.
- 주문의 총 갯수는 20개를 넘으면 안된다.
- 주문 메뉴는 중복될 수 없다.
- 존재하는 메뉴로 주문을 해야한다.
- 메뉴 주문 갯수는 양수여야 한다.

비즈니스 로직과 관련되지 않는 검증

- 날짜 입력은 숫자여야 한다.
- 존재하지 않는 날짜를 입력할 수 없다.
- 메뉴 주문 갯수는 숫자여야 한다.

만약 콘솔 입력이 아니라 GUI를 통해 날짜나 메뉴 갯수를 클릭하는 방식이 요구사항 이었다면 어떨까? 그럴 땐 입력이 당연히 숫자일 것이므로 관련한 검증이 필요하지 않을 것이다. 즉, 비즈니스 로직과 관련되지 않은
검증만 변화한다.
변화의 이유가 다르다면 분리해야한다.

따라서 DTO에는 비즈니스 로직과 관련되지 않는 검증을 진행하고, 도메인 클래스에서는 비즈니스와 관련된 검증 책임을 부여했다.

### 이벤트 추상화

이번 요구사항은 이전 프로젝트의 요구사항보다 복잡하다. 표를 그려보자.

|              | 크리스마스 디데이 할인                                         | 평일 할인                                    | 주말할인                             | 특별할인                            | 증정이벤트                          |
|--------------|------------------------------------------------------|------------------------------------------|----------------------------------|---------------------------------|--------------------------------|
| 기간 적용 조건     | X(할인 계산 방법에 포함)                                      | 1일 ~ 31일                                 | 1일 ~ 31일                         | 1일 ~ 31일                        | 1일 ~ 31일                       |
| 총 주문금액 적용 조건 | 10000원 이상                                            | 10000원 이상                                | 10000원 이상                        | 10000원 이상                       | X(증정 품목 계산 방법에 포함)             |
| 할인 금액        | 예약일이 1일이면 1000원 할인 하루 지날때 마다 100원 추가 할인, 26일부터 0원 할인 | 예약일이 평일(일,월,화,수,목)이면 디저트 메뉴 1개당 2023원 할인 | 예약일이 주말(금, 토)면 메인메뉴 1개당 2023원 할인 | 예약일이 이벤트 달력에 별이 있는 날이면 1000원 할인 | X                              |
| 증정 품목        | X                                                    | X                                        |                                  | X                               | 총 주문 금액이 120000이상힐 시 샴페인 1개 증정 |

전체를 살펴보면 적용 조건과 이벤트 적용으로 이벤트를 분리할 수 있다. `EventCondition`과 `EventPolicy`로 나누어서 `Event`클래스를 구현해보면 다음과 같다.

```java
public class Event {

    private final String eventName;
    private final EventCondition eventCondition;
    private final EventPolicy eventPolicy;

    public Event(String eventName, EventCondition eventCondition, EventPolicy eventPolicy) {
        this.eventName = eventName;
        this.eventCondition = eventCondition;
        this.eventPolicy = eventPolicy;
    }

    public Money calculateDiscountAmount(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            return eventPolicy.calculateDiscountAmount(reservation);
        }

        return Money.ZERO;
    }

    public Money calculateBenefitAmount(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            Money discountAmount = eventPolicy.calculateDiscountAmount(reservation);
            Optional<MenuQuantity> giftOptional = eventPolicy.receiveGift(reservation);
            if (giftOptional.isEmpty()) {
                return discountAmount;
            }

            MenuQuantity gift = giftOptional.get();
            return discountAmount.add(gift.calculateCost());
        }

        return Money.ZERO;
    }

    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            return eventPolicy.receiveGift(reservation);
        }

        return Optional.empty();
    }

    public String getEventName() {
        return eventName;
    }
}
```

`Event`클래스는 이벤트 이름, 이벤트 조건, 이벤트 정책으로 나뉜다. 할인 금액 계산, 혜택 금액 계산, 증정품 수령 3가지 기능이 있으며, 혜택 금액은 할인 금액과 증정품의 가격을 더한 금액이다.

조건을 검사하고 조건에 맞지 많으면 `Optional.empty()`나 `Money.ZERO`를 반환한다. `PromotionService`에서 `List<Event>`를 멤버 변수로 가지고 있으며 `List`에서
이벤트들의 결과를 계산해 합산하는 역할을 한다.

위 처럼 이벤트 조건과 이벤트 정책을 분리해 구현했기 때문에 이벤트 요구사항이 변경되어도 유연한 대처가 가능하다.

이벤트 조건과 이벤트 정책을 구현해 조립하면 유연한 이벤트 설계가 가능하다.

```java

public class Sample {
    private static Event giftEvent(EventCondition eventCondition, MenuRepository menuRepository) {
        return new Event("증정 이벤트",
                eventCondition,
                new GiftEventPolicy(
                        menuRepository,
                        Money.of(120_000),
                        "샴페인",
                        1
                )
        );
    }

    private static Event specialDayDiscountEvent(EventCondition eventCondition) {
        return new Event(
                "특별 할인",
                eventCondition,
                new SpecialDayEventPolicy(
                        Money.of(1_000L),
                        Set.of(
                                eventYearMonth.atDay(3),
                                eventYearMonth.atDay(10),
                                eventYearMonth.atDay(17),
                                eventYearMonth.atDay(24),
                                eventYearMonth.atDay(25),
                                eventYearMonth.atDay(31)
                        )
                ));
    }
}
```

만약 증정품과 할인을 동시에 제공하는 이벤트를 설계하려면 `EventPolicy`를 구현해 적용할 수 있다. 이벤트를 추상화 한 덕에 요구사항이 변경되어도 유연하게 대처할 수 있다.

```java
public interface EventCondition {
    boolean isSatisfiedBy(Reservation reservation);
}
```

`EventCondition`은 위와 같은 메서드를 가지고 있다. 만약 여러 이벤트 조건을 중복 적용해야 한다면 어떨까? `CompositeEventCondition`의 구현으로 이를 해결할 수 있다.

```java
public class CompositeEventCondition implements EventCondition {

    private final List<EventCondition> eventConditions;

    public CompositeEventCondition(EventCondition... eventConditions) {
        this.eventConditions = List.of(eventConditions);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        for (EventCondition eventCondition : eventConditions) {
            if (!eventCondition.isSatisfiedBy(reservation)) {
                return false;
            }
        }

        return true;
    }
}
```

멤버로 `List<EventCondition>`을 가지고 있고 여러 이벤트 조건을 순회하면서 하나라도 조건이 맞지 않으면 이벤트 조건 불만족을 반환하고 모두 조건에 부합하면 이벤트 조건 만족을 반환한다.

따라서 기간 조건과, 최소 주문 금액 조건 등의 중복 조건을 가지는 이벤트를 다음과 같이 생성할 수 있다. 이벤트 기간이 10월과 12월로 띄엄 띄엄 있게 요구사항이 바뀌었다고
하면 `PeriodEventCondition`을 여러개
조합하면 될 것이다.

```java
import java.time.YearMonth;

public class Sample {
    private static Event weekendDiscountEvent() {
        return new Event(
                "주말 할인",
                new CompositeEventCondition(
                        new PeriodEventCondition.wholeMonth(YearMonth.of(2023, 10)),
                        new PeriodEventCondition.wholeMonth(YearMonth.of(2023, 12))
                ),
                new WeekendEventPolicy(MenuType.MAIN_DISH,
                        Money.of(2_023L))
        );
    }
}
```

이로써 유연한 이벤트를 만들 수 있게 되었다.

### 도메인 서비스 격리 & 패키지간의 의존성 관리

패키지간의 의존성이 낮아질 수 있도록 패키지 구조를 짰다. 특히, 도메인과 서비스가 다른 레이어에 의존하지 않도록 했다. 또한 주입받을 인터페이스를 같은 패키지에 둬서 의존성이 역전될 수 있도록 했다.

실제 구현체들은 패키지 외부에 두고 의존하는 인터페이스를 같은 패키지에 둠으로써 컴파일 타임 의존성을 해결했다. 위의 방법을 `SEPERATED INTERFACE` 패턴을 지켰다.

패키지간의 의존성으 간략하게 그려보면 다음과 같다. (생략된 패키지도 존재한다.)

![](https://i.imgur.com/ysYKwWe.png)

중요한 것은 도메인과 서비스에서 나가는 의존성이 없다는 것이다. 따라서 해당 패키지를 분리해서 서비스와 도메인을 재사용할 수 있다.

## 구현할 기능 목록 정리

### 공통 사항

- [x] `camp.nextstep.edu.missionutils`에서 제공하는 `Console` API를 사용하여 구현해야 한다.
    - [x] 사용자가 입력하는 값은 `camp.nextstep.edu.missionutils.Console`의 `readLine()`을 활용한다.
- [x] 사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
    - [x] `Exception`이 아닌 `IllegalArgumentException`, `IllegalStateException` 등과 같은 명확한 유형을 처리한다.
- [x] 도메인 로직에 단위 테스트를 구현해야 한다. 단, UI(System.out, System.in, Scanner) 로직은 제외한다.
    - [x] JUnit 5와 AssertJ를 이용하여 본인이 정리한 기능 목록이 정상 동작함을 테스트 코드로 확인한다.
    - [x] 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 분리해 구현한다.
- [x] else 예약어를 쓰지 않는다. switch/case도 허용하지 않는다. 3항연산자도 사용하지 않는다.
- [x] indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현한다. 2까지만 허용한다.
- [x] 함수(또는 메서드)의 길이가 15라인을 넘어가지 않도록 구현한다.
- [x] 프로그래밍 요구 사항에서 달리 명시하지 않는 한 파일, 패키지 이름을 수정하거나 이동하지 않는다.
- [x] 프로그램 구현이 완료되면 `ApplicationTest`의 모든 테스트가 성공해야 한다. **테스트가 실패할 경우 0점 처리한다.**
- [x] 프로그램 종료 시 `System.exit()`를 호출하지 않는다.
- [x] [Java 코드 컨벤션](https://github.com/woowacourse/woowacourse-docs/tree/master/styleguide/java) 가이드를 준수하며 프로그래밍한다.
- [x] `build.gradle` 파일을 변경할 수 없고, 외부 라이브러리를 사용하지 않는다.
- [x] JDK 17 버전에서 실행 가능해야 한다.
- [x] 프로그램 실행의 시작점은 `Application`의 `main()`이다.

### 기능 목록

- [x] 웰컴 메세지 출력

[예시]

```
안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.
```

- [x] 방문 날짜 입력
    - [x] 방문할 날짜는 1 이상 31 이하의 숫자로만 입력
    - [x] 1 이상 31 이하의 숫자가 아닌 경우, "[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요."라는 에러 메시지 출력 후 재입력 요청

[예시]

```
12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)
26 
```

- [x] 주문 메뉴 입력
    - [x] 음료만 주문 시, 주문할 수 없습니다.
    - [x] 메뉴는 한 번에 최대 20개까지만 주문할 수 있습니다.  
      (e.g. 시저샐러드-1, 티본스테이크-1, 크리스마스파스타-1, 제로콜라-3, 아이스크림-1의 총개수는 7개)
    - [x] 메뉴의 개수는 1 이상의 숫자만 입력되도록 해주세요. 이외의 입력값은 "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 메뉴 형식이 예시와 다른 경우, "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 중복 메뉴를 입력한 경우(e.g. 시저샐러드-1,시저샐러드-1), "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요.
    - [x] 고객이 메뉴판에 없는 메뉴를 입력하는 경우, "[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요."라는 에러 메시지를 보여 주세요. 메뉴판은 다음과 같다.

[메뉴판]

```
<애피타이저>
양송이수프(6,000), 타파스(5,500), 시저샐러드(8,000)

<메인>
티본스테이크(55,000), 바비큐립(54,000), 해산물파스타(35,000), 크리스마스파스타(25,000)

<디저트>
초코케이크(15,000), 아이스크림(5,000)

<음료>
제로콜라(3,000), 레드와인(60,000), 샴페인(25,000)
```

[예시]

```
주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)
티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1
```

- [x] 크리스마스 디데이 할인 적용
    - [x] 총주문 금액 10,000원 이상부터 이벤트 적용
    - [x] 기간: 2023.12.1 ~ 2023.12.25
    - [x] 1,000원으로 시작하여 크리스마스가 다가올수록 날마다 할인 금액이 100원씩 증가
    - [x] 총주문 금액에서 해당 금액만큼 할인 (e.g. 시작일인 12월 1일에 1,000원, 2일에 1,100원, ..., 25일엔 3,400원 할인)
    - [x] 시작인 전이거나, 디데이를 지나면 0원을 할인한다.

- [x] 평일 할인 적용
    - [x] 총주문 금액 10,000원 이상부터 이벤트 적용
    - [x] 기간: 2023.12.1 ~ 2023.12.31
    - [x] 평일(일요일~목요일)에는 디저트 메뉴를 메뉴 1개당 2,023원 할인

- [x] 주말 할인 적용
    - [x] 총주문 금액 10,000원 이상부터 이벤트 적용
    - [x] 기간: 2023.12.1 ~ 2023.12.31
    - [x] 주말(금요일, 토요일)에는 메인 메뉴를 메뉴 1개당 2,023원 할인

- [x] 특별 할인 적용
    - [x] 총주문 금액 10,000원 이상부터 이벤트 적용
    - [x] 기간: 2023.12.1 ~ 2023.12.31
    - [x] 이벤트 달력에 별이 있으면 총주문 금액에서 1,000원 할인
    - [x] 달력은 다음과 같다.

![](../image.png)

- [x] 증정 이벤트 적용
    - [x] 총주문 금액 10,000원 이상부터 이벤트 적용
    - [x] 기간: 2023.12.1 ~ 2023.12.31
    - [x] 할인 전 총주문 금액이 12만 원 이상일 때, 샴페인 1개 증정

- [x] 이벤트 배지 부여
    - [x] 총주문 금액 10,000원 이상부터 이벤트 적용
    - [x] 기간: 2023.12.1 ~ 2023.12.31
    - [x] 총 혜택 금액에 따라 다른 이벤트 배지를 부여한다. 배지는 다음과 같다.
        - 5천 원 이상: 별
        - 1만 원 이상: 트리
        - 2만 원 이상: 산타

- [x] 이벤트 혜택 출력
    - [x] 주문 메뉴 출력
        - [x] 주문 메뉴의 출력 순서는 자유롭게 출력
    - [x] 할인 전 총주문 금액 출력
        - [x] 금액은 `###,###` 형식으로 출력한다.
    - [x] 증정 메뉴 출력
        - [x] 증정 갯수를 함께 출력한다.
        - [x] 증정 이벤트에 해당하지 않는 경우, 증정 메뉴 "없음"으로 출력
    - [x] 혜택 내역 출력
        - [x] 고객에게 적용된 이벤트 내역만 출력
        - [x] 적용된 이벤트가 하나도 없다면 혜택 내역 "없음"으로 출력
        - [x] 혜택 내역에 여러 개의 이벤트가 적용된 경우, 출력 순서는 자유롭게 출력
    - [x] 총혜택 금액 출력
        - [x] 총혜택 금액 = 할인 금액의 합계 + 증정 메뉴의 가격
    - [x] 할인 후 예상 결제 금액 출력
        - [x] 할인 후 예상 결제 금액 = 할인 전 총주문 금액 - 할인 금액
    - [x] 이벤트 배지 출력
        - [x] 총혜택 금액에 따라 이벤트 배지의 이름을 다르게 보여 주세요.
        - [x] 이벤트 배지가 부여되지 않는 경우, "없음"으로 보여 주세요.

[예시]

```
<주문 메뉴>
티본스테이크 1개
바비큐립 1개
초코케이크 2개
제로콜라 1개
 
<할인 전 총주문 금액>
142,000원
 
<증정 메뉴>
샴페인 1개
 
<혜택 내역>
크리스마스 디데이 할인: -1,200원
평일 할인: -4,046원
특별 할인: -1,000원
증정 이벤트: -25,000원
 
<총혜택 금액>
-31,246원
 
<할인 후 예상 결제 금액>
135,754원
 
<12월 이벤트 배지>
산타
```

[예시 - 이벤트 해당 사항 있는 경우]

```
안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.
12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)
3
주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)
티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1
12월 3일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
 
<주문 메뉴>
티본스테이크 1개
바비큐립 1개
초코케이크 2개
제로콜라 1개
 
<할인 전 총주문 금액>
142,000원
 
<증정 메뉴>
샴페인 1개
 
<혜택 내역>
크리스마스 디데이 할인: -1,200원
평일 할인: -4,046원
특별 할인: -1,000원
증정 이벤트: -25,000원
 
<총혜택 금액>
-31,246원
 
<할인 후 예상 결제 금액>
135,754원
 
<12월 이벤트 배지>
산타
```

[전체 예시 - 이벤트 해당 사항 없는 경우]

```
안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.
12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)
26 
주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)
타파스-1,제로콜라-1 
12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!
 
<주문 메뉴>
타파스 1개
제로콜라 1개

<할인 전 총주문 금액>
8,500원
 
<증정 메뉴>
없음
 
<혜택 내역>
없음
 
<총혜택 금액>
0원
 
<할인 후 예상 결제 금액>
8,500원
 
<12월 이벤트 배지>
없음
```

---
