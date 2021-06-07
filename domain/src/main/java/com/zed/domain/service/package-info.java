package com.zed.domain.service;

/**
 *
 * 2.4.1 - 用Domain Primitive封装跟实体无关的无状态计算逻辑
 *
 *
 *
 * 在这个案例里使用ExchangeRate来封装汇率计算逻辑：
 *
 * BigDecimal exchangeRate = BigDecimal.ONE;
 * if (sourceAccountDO.getCurrency().equals(targetCurrency)) {
 *     exchangeRate = yahooForex.getExchangeRate(sourceAccountDO.getCurrency(), targetCurrency);
 * }
 * BigDecimal sourceAmount = targetAmount.divide(exchangeRate, RoundingMode.DOWN);
 * 变为：
 *
 * ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(sourceAccount.getCurrency(), targetMoney.getCurrency());
 * Money sourceMoney = exchangeRate.exchangeTo(targetMoney);
 *
 *
 *
 *
 * 2.4.3 - 用Domain Service封装多对象逻辑
 *
 *
 * 在这个案例里，我们发现这两个账号的转出和转入实际上是一体的，也就是说这种行为应该被封装到一个对象中去。特别是考虑到未来这个逻辑可能会产生变化：比如增加一个扣手续费的逻辑。这个时候在原有的TransferService中做并不合适，在任何一个Entity或者Domain Primitive里也不合适，需要有一个新的类去包含跨域对象的行为。这种对象叫做Domain Service。
 *
 * 我们创建一个AccountTransferService的类：
 *
 *
 *
 * public interface AccountTransferService {
 *     void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate);
 * }
 *
 * public class AccountTransferServiceImpl implements AccountTransferService {
 *     private ExchangeRateService exchangeRateService;
 *
 *     @Override
 *     public void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) {
 *         Money sourceMoney = exchangeRate.exchangeTo(targetMoney);
 *         sourceAccount.deposit(sourceMoney);
 *         targetAccount.withdraw(targetMoney);
 *     }
 * }
 *
 *
 * 而原始代码则简化为一行：
 *
 *
 *
 * accountTransferService.transfer(sourceAccount, targetAccount, targetMoney, exchangeRate);
 *
 */