CREATE TABLE products
(
    productID   SERIAL NOT NULL PRIMARY KEY,
    name        varchar,
    price       double precision,
    description varchar,
    createDate  timestamp,
    updateDate  timestamp
);

CREATE TABLE users
(
    userID   SERIAL NOT NULL PRIMARY KEY,
    username varchar,
    password varchar,
    sole     varchar
);

CREATE TABLE sessions
(
    sessionId     SERIAL NOT NULL PRIMARY KEY,
    token         varchar,
    userInSession varchar,
    expireDate    bigint
);

CREATE TABLE cart
(
    cartItemId SERIAL NOT NULL PRIMARY KEY,
    userId     bigint,
    productId  bigint
);


