[![CircleCI](https://circleci.com/gh/sirolf2009/bitfinex-api.svg?style=svg)](https://circleci.com/gh/sirolf2009/bitfinex-api)

# bitfinex-api

This project is a wrapper for the bitfinex REST api. Along with some higher level functions such as tickers.
There is a WIP project that will eventually do the same as this library, but with websockets. You can find it here https://github.com/sirolf2009/bitfinex-wss

## Authorized and unauthorized

For some calls, such as account balance, you need to be authenticated. Authentication requires a Bitfinex public key and secret key.
These can be generated via the API option on the website. Once you have your keys, you need to call an authenticated call via the library. This will fail, but also generate a template file under ~/.bitfinex/keys.
You can paste your public and private key into that file and from then you can make authenticated calls.
See http://docs.bitfinex.com/#rest for an overview which calls require authentication.

## Calls

The following calls are supported:
- pubticker
- stats
- lendbook
- margin_infos
- new_order
- trades
- lends

The project also contains some utility methods. They are methods not supported by the REST api, but are there for convenience. The following utillity calls are supported.
- marginBuyMarket - Place a market buy order for a certain amount
- marginSellMarket - Place a market sell order for a certain amount
- getCandlesticks - Returns a list of candlesticks for a certain timeframe
- ticker - Creates a listener for new trades
- candleTicker - creates a listener for new candlesticks for a certain timeframe
- heikenAshiTicker - creates a listener for new heiken ashi candlesticks for a certain timeframe
- getSentiment - return a sentiment in percentages for a given symbol

## Examples

To do anything, you must call 
```java
Bitfinex bitfinex = new Bitfinex();
```

Using this object, you can make your calls. Getting the latest pubticker info from BTCUSD for instance works like this
```java
System.out.println(bitfinex.pubticker(Symbols.BTCUSD));
```
