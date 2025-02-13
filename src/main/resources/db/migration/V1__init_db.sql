DROP TABLE IF EXISTS "authors";
CREATE TABLE "authors" (
   "id" serial PRIMARY KEY,
   "age" smallint,
   "description" VARCHAR(512),
   "image" VARCHAR(512),
   "name" VARCHAR(512)
);

DROP TABLE IF EXISTS "books";
CREATE TABLE "books" (
   "isbn" VARCHAR(19) PRIMARY KEY,
   "description" VARCHAR(2048),
   "image" VARCHAR(512),
   "title" VARCHAR(512),
   "author_id" bigint NOT NULL REFERENCES "authors" ("id")
);