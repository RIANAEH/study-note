package ellie.study.record;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RecordTest {

    @Test
    void 레코드는_값객체이다() {
        Money won5000 = new Money(5_000, Unit.WON);
        Money won5000_ = new Money(5_000, Unit.WON);
        Money dollar5000 = new Money(5_000, Unit.DOLLAR);

        assertThat(won5000.equals(won5000_)).isTrue();
        assertThat(won5000.equals(dollar5000)).isFalse();
    }

    @Test
    void 레코드도_연산메서드를_따로_구현해야한다() {
        assertThat(Money.won(5_000).plus(Money.won(1_000)))
                .isEqualTo(Money.won(6_000));
        assertThatThrownBy(() -> Money.won(5_000).plus(Money.dollar(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    record Money(long value, Unit unit) {

        public static Money won(long value) {
            return new Money(value, Unit.WON);
        }

        public static Money dollar(long value) {
            return new Money(value, Unit.DOLLAR);
        }

        public Money plus(Money money) {
            if (!this.unit.equals(money.unit)) {
                throw new IllegalArgumentException("단위가 같은 경우에만 연산 가능합나다.");
            }
            return new Money(this.value + money.value(), this.unit);
        }
    }

    enum Unit {
        WON, DOLLAR
    }
}
