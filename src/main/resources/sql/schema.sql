CREATE TABLE IF NOT EXISTS recipes (
  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
  name                   VARCHAR(100) NOT NULL,
  owner                  VARCHAR(100) NOT NULL,
  date                   BIGINT NOT NULL,
  ingredients            VARCHAR(1000) NOT NULL,
  instructions           VARCHAR(2000) NOT NULL,
  tags                   VARCHAR(1000) NOT NULL,
  servings               INT,
  calories               INT,
  macroNutrients         VARCHAR(500),
  rating                 INT
);