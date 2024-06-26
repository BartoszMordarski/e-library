INSERT INTO books (title, author, isbn, status) VALUES ('The Hobbit', 'J.R.R. Tolkien', '978-0547928227', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Fellowship of the Ring', 'J.R.R. Tolkien', '978-0547928210', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Two Towers', 'J.R.R. Tolkien', '978-0547928203', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Return of the King', 'J.R.R. Tolkien', '978-0547928197', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('Harry Potter and the Sorcerers Stone', 'J.K. Rowling', '978-0590353427', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', '978-0439064873', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', '978-0439136365', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('Harry Potter and the Goblet of Fire', 'J.K. Rowling', '978-0439139601', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('A Game of Thrones', 'George R.R. Martin', '978-0553593716', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('A Clash of Kings', 'George R.R. Martin', '978-0553579901', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('A Storm of Swords', 'George R.R. Martin', '978-0553573428', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('A Feast for Crows', 'George R.R. Martin', '978-0553582024', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('A Dance with Dragons', 'George R.R. Martin', '978-0553582017', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Name of the Wind', 'Patrick Rothfuss', '978-0756404741', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Wise Mans Fear', 'Patrick Rothfuss', '978-0756407919', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('Mistborn: The Final Empire', 'Brandon Sanderson', '978-0765350381', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Well of Ascension', 'Brandon Sanderson', '978-0765356130', 'AVAILABLE');
INSERT INTO books (title, author, isbn, status) VALUES ('The Hero of Ages', 'Brandon Sanderson', '978-0765356147', 'AVAILABLE');


INSERT INTO roles (id, name) VALUES (1, 'ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'USER');


INSERT INTO users (password, username) VALUES ('$2a$10$DAJ9VREem1a/D9QeLX3Xu.cRNmN44UIyXEwzMY4SyQgnazXRLPszS', 'user1');
INSERT INTO users (password, username) VALUES ('$2a$10$n3Sa4V/uZ2KuIijJRp7QwuAHPgGr3wu0N3qLk1dCCL41BuTq1cOke', 'user2');
INSERT INTO users (password, username) VALUES ('$2a$10$iqIrQwGmbJFoagbKzShX6.GiYG1Hal8c8glF853IVaN3uqCpAfxKy', 'user3');
INSERT INTO users (password, username) VALUES ('$2a$10$5FiSpFuVpdCVu4NG1CbSwusLCTbOHL5ToOinU7F.VIlemtM66hmlG', 'user4');


INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 2);


INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('Great book!', 5, NOW(), 1, 1);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('This book was an amazing read! Highly recommend.', 5, NOW(), 2, 1);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('I found the plot to be quite predictable.', 2, NOW(), 1, 2);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('A fascinating tale with rich characters.', 4, NOW(), 3, 3);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('The pacing was a bit slow, but overall enjoyable.', 3, NOW(), 2, 1);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('Couldn''t put it down, a real page-turner!', 5, NOW(), 4, 2);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('Not my cup of tea, but well written.', 3, NOW(), 2, 5);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('Engaging from start to finish, loved it!', 5, NOW(), 6, 1);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('An interesting read with a few twists.', 4, NOW(), 3, 2);
INSERT INTO reviews (content, rating, created_at, book_id, user_id) VALUES ('Didn''t live up to the hype, unfortunately.', 2, NOW(), 5, 3);


INSERT INTO loans (loan_date, return_date, book_id, user_id) VALUES ('2024-01-01', NULL, 1, 1);
UPDATE books SET status = 'LOANED' WHERE id = 1;
INSERT INTO loans (loan_date, return_date, book_id, user_id) VALUES ('2024-02-01', '2024-03-01', 2, 2);
INSERT INTO loans (loan_date, return_date, book_id, user_id) VALUES ('2024-03-01', NULL, 3, 3);
UPDATE books SET status = 'LOANED' WHERE id = 3;
INSERT INTO loans (loan_date, return_date, book_id, user_id) VALUES ('2024-04-01', '2024-05-01', 4, 4);
INSERT INTO loans (loan_date, return_date, book_id, user_id) VALUES ('2024-05-01', NULL, 5, 1);
UPDATE books SET status = 'LOANED' WHERE id = 5;