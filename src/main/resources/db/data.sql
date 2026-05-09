-- =========================================================
-- PARTY
-- =========================================================

INSERT INTO PARTY (
    PARTY_ID,
    PARTY_NAME,
    PARTY_TYPE,
    TAX_ID,
    COUNTRY,
    SECTOR,
    RATING,
    IS_ACTIVE
) VALUES
    (1, 'Banco de Chile', 'COUNTERPARTY', '97-004-000-5', 'CL', 'Banking', 'AA', 1),

    (2, 'Tesoreria General', 'ISSUER', '60-805-000-0', 'CL', 'Government', 'AAA', 1),

    (3, 'Falabella S.A.', 'ISSUER', '90-743-000-0', 'CL', 'Retail', 'A+', 1),

    (4, 'BTG Pactual', 'BROKER', '30-306-294-3', 'BR', 'Banking', 'AA-', 1),

    (5, 'Goldman Sachs', 'COUNTERPARTY', '13-5108880', 'US', 'Banking', 'A+', 1);



-- =========================================================
-- INSTRUMENT
-- =========================================================

INSERT INTO INSTRUMENT (
    INSTRUMENT_ID,
    TICKER,
    INSTRUMENT_NAME,
    INSTRUMENT_TYPE,
    ASSET_CLASS,
    ISSUER_ID,
    CURRENCY,
    MATURITY_DATE,
    COUPON_RATE,
    NOMINAL_VALUE,
    IS_ACTIVE
) VALUES
      (
          1,
          'BTP-0230',
          'Bono Tesoreria 2030',
          'BOND',
          'FIXED_INCOME',
          2,
          'CLP',
          DATE '2030-07-01',
          3.50,
          1000000,
          1
      ),

      (
          2,
          'BCU-0528',
          'BCU 5Y UF 2028',
          'BOND',
          'FIXED_INCOME',
          2,
          'CLF',
          DATE '2028-05-15',
          2.80,
          500000,
          1
      ),

      (
          3,
          'FALABELLA',
          'Falabella Equity',
          'EQUITY',
          'EQUITY',
          3,
          'CLP',
          NULL,
          NULL,
          NULL,
          1
      );



-- =========================================================
-- TX_TRANSACTION
-- =========================================================

INSERT INTO TX_TRANSACTION (
    TXN_ID,
    TXN_DATE,
    TXN_TYPE,
    QUANTITY,
    PRICE,
    AMOUNT,
    CURRENCY,
    STATUS,
    SETTLEMENT_DATE,
    PORTFOLIO_ID,
    INSTRUMENT_ID,
    COUNTERPARTY_ID,
    CREATED_AT
) VALUES
      (
          1,
          DATE '2026-01-10',
          'BUY',
          50000,
          98.50,
          4925000,
          'CLP',
          'SETTLED',
          DATE '2026-01-12',
          100,
          1,
          1,
          CURRENT_TIMESTAMP
      ),

      (
          2,
          DATE '2026-01-15',
          'BUY',
          10000,
          102.30,
          1023000,
          'CLF',
          'SETTLED',
          DATE '2026-01-17',
          100,
          2,
          1,
          CURRENT_TIMESTAMP
      ),

      (
          3,
          DATE '2026-01-20',
          'BUY',
          5000,
          2350,
          11750000,
          'CLP',
          'SETTLED',
          DATE '2026-01-22',
          200,
          3,
          4,
          CURRENT_TIMESTAMP
      );