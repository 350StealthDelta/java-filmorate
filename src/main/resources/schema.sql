-- Exported from QuickDBD: https://www.quickdatabasediagrams.com/
-- NOTE! If you have used non-SQL datatypes in your design, you will have to change these here.

CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID      int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    FILM_NAME    varchar(100) NOT NULL,
    DESCRIPTION  varchar(200) NOT NULL,
    RELEASE_DATE Date         NOT NULL,
    DURATION     int          NOT NULL,
    RATE         int          NOT NULL,
    MPA_RATE     int          NOT NULL,
    UNIQUE (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION)
);

CREATE TABLE IF NOT EXISTS GENRES
(
    GENRE_ID   int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    GENRE_NAME varchar(100) NOT NULL,
    UNIQUE (GENRE_NAME)
);

CREATE TABLE IF NOT EXISTS FILMGENRES
(
    FILM_ID  int NOT NULL,
    GENRE_ID int NOT NULL,
    UNIQUE (FILM_ID, GENRE_ID)
);

CREATE TABLE IF NOT EXISTS MPAS
(
    MPA_ID          int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    MPA_NAME        varchar(100) NOT NULL,
    MPA_DESCRIPTION varchar(500) NOT NULL,
    UNIQUE (MPA_NAME)
);

CREATE TABLE IF NOT EXISTS FILMLIKERS
(
    FILM_ID int NOT NULL,
    USER_ID int NOT NULL,
    UNIQUE (FILM_ID, USER_ID)
);

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID    int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    USER_EMAIL varchar(50) NOT NULL,
    USER_LOGIN varchar(50) NOT NULL,
    USER_NAME  varchar(50) NOT NULL,
    BIRTHDAY   Date        NOT NULL,
    UNIQUE (USER_EMAIL)
);

CREATE TABLE IF NOT EXISTS FRIENDSHIP
(
    USER_ID   int NOT NULL,
    FRIEND_ID int NOT NULL,
    UNIQUE (USER_ID, FRIEND_ID)
);

ALTER TABLE FILMS
    ADD CONSTRAINT FK_FILMS_MPA_RATE FOREIGN KEY (MPA_RATE)
        REFERENCES MPAS (MPA_ID);

ALTER TABLE FILMGENRES
    ADD CONSTRAINT FK_FILMGENRES_FILM_ID FOREIGN KEY (FILM_ID)
        REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;

ALTER TABLE FILMGENRES
    ADD CONSTRAINT FK_FILMGENRES_GENRE_ID FOREIGN KEY (GENRE_ID)
        REFERENCES GENRES (GENRE_ID) ON DELETE CASCADE;

ALTER TABLE FILMLIKERS
    ADD CONSTRAINT FK_FILMLIKERS_FILM_ID FOREIGN KEY (FILM_ID)
        REFERENCES FILMS (FILM_ID) ON DELETE CASCADE;

ALTER TABLE FILMLIKERS
    ADD CONSTRAINT FK_FILMLIKERS_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID) ON DELETE CASCADE;

ALTER TABLE FRIENDSHIP
    ADD CONSTRAINT FK_FRIENDSHIP_USER_ID FOREIGN KEY (USER_ID)
        REFERENCES USERS (USER_ID) ON DELETE CASCADE;

ALTER TABLE FRIENDSHIP
    ADD CONSTRAINT "FK_FRIENDSHIP_FRIEND_ID" FOREIGN KEY (FRIEND_ID)
        REFERENCES USERS (USER_ID) ON DELETE CASCADE;