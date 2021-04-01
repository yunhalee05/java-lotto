package lotto.domain;

import lotto.exception.LottoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoBuyerTest {

    @DisplayName("최소 구매가격인 1000원을 안지켰을 경우 에러처리")
    @ParameterizedTest
    @ValueSource(ints = {900, 500, 999, 700})
    void lottoPurchaseUnderMin(int lottoPurchaseAmount) {
        assertThatThrownBy(() -> {
            LottoBuyer.of(lottoPurchaseAmount, 3);
        }).isInstanceOf(LottoException.class)
                .hasMessage("로또의 최소 구매금액은 1000원입니다.");
    }

    @DisplayName("1000원 단위로 구매를 하지 않았을 경우 에러처리")
    @ParameterizedTest
    @ValueSource(ints = {1001, 2002, 3010, 4099})
    void lottoPurchaseAmountUnit(int lottoPurchaseAmount) {
        assertThatThrownBy(() -> {
            LottoBuyer.of(lottoPurchaseAmount, 5);
        }).isInstanceOf(LottoException.class)
                .hasMessage("구매금액은 1000원 단위로 입력해주세요.");
    }

    @DisplayName("수동로또 구매수량이 총 구매수량을 넘였을 경우 에러처리")
    @ParameterizedTest
    @ValueSource(ints = {2000, 3000, 4000})
    void manualLottoOverPurchaseCount(int lottoPurchaseAmount) {
        assertThatThrownBy(() -> {
            LottoBuyer.of(lottoPurchaseAmount, 5);
        }).isInstanceOf(LottoException.class)
                .hasMessage("수동 로또 구매수량은 로또 구매 금액을 넘을수 없습니다.");
    }

    @DisplayName("수동로또 구매수량이 0 밑일 경우 에러처리")
    @ParameterizedTest
    @CsvSource(value = {"2000:-1", "3000:-2", "4000:-3"}, delimiter = ':')
    void manualLottoCountUnderZero(int lottoPurchaseAmount, int lottoManualCount) {
        assertThatThrownBy(() -> {
            LottoBuyer.of(lottoPurchaseAmount, lottoManualCount);
        }).isInstanceOf(LottoException.class)
                .hasMessage("수동 로또 구매수량은 0개 이상입니다.");
    }

    @DisplayName("로또 구입금액 확인")
    @ParameterizedTest
    @CsvSource(value = {"14000:14", "20000:20", "30000:30"}, delimiter = ':')
    void LottoQuantity(int lottoPurchaseAmount, int lottoQuantity) {
        LottoBuyer lottoBuyer = LottoBuyer.of(lottoPurchaseAmount, 10);
        assertThat(lottoBuyer.getLottoQuantity()).isEqualTo(lottoQuantity);
    }
}