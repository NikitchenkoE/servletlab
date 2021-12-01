CREATE TABLE products
(
    productID   IDENTITY NOT NULL PRIMARY KEY,
    name        varchar,
    price       double precision,
    description varchar,
    createDate  timestamp,
    updateDate  timestamp
);

CREATE TABLE users
(
    userID   IDENTITY NOT NULL PRIMARY KEY,
    username varchar,
    password varchar,
    sole     varchar
);

CREATE TABLE sessions
(
    sessionId     IDENTITY NOT NULL PRIMARY KEY,
    token         varchar,
    userInSession varchar,
    expireDate    bigint
);

CREATE TABLE cart
(
    cartItemId IDENTITY NOT NULL PRIMARY KEY,
    userId     bigint,
    productId  bigint
);


