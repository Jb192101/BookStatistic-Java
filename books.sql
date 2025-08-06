CREATE TABLE book (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    author VARCHAR(255),
    complete_pages INTEGER,
    all_pages INTEGER,
    percent_of_readed REAL,
    start_of_reading DATE,
    end_of_reading DATE,
    rating VARCHAR(20)
);

CREATE TABLE speed_stat (
    month INTEGER NOT NULL,
    year INTEGER NOT NULL,
    count INTEGER NOT NULL,
    PRIMARY KEY (month, year)
);

CREATE TABLE month_stat (
    month INTEGER NOT NULL,
    year INTEGER NOT NULL,
    count INTEGER NOT NULL,
    PRIMARY KEY (month, year)
);