[when]购物金额达 {money}=guest:Guest(money>={money})
[when]金额不足 {money}=guest:Guest(money<{money})
[then]实际金额减少 {reduceMoney}=guest.buy({reduceMoney});
[then]无法优惠全额购买=guest.buy();