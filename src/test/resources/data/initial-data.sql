DELETE FROM reviews;
DELETE FROM users;

insert into users (id, first_name, last_name, email, password, role, created_at, updated_at)
values
(1, 'John', 'Doe', 'john.doe@example.com', 'password123', 'ADMIN', '2022-09-20', '2022-09-20'),
(2, 'Jane', 'Smith', 'jane.smith@example.com', 'password456', 'REVIEWER', '2022-09-20', '2022-09-20'),
(3, 'Bob', 'Johnson', 'bob.johnson@example.com', 'password789', 'REVIEWER', '2022-09-20', '2022-09-20'),
(4, 'Alice', 'Williams', 'alice.williams@example.com', 'password321', 'ADMIN', '2022-09-20', '2022-09-20'),
(5, 'Tom', 'Brown', 'tom.brown@example.com', 'password654', 'REVIEWER', '2022-09-20', '2022-09-20');

insert into reviews (id, book_id, reviewer_id, review, review_date, created_at, updated_at)
values
(1, 'iWA-DwAAQBAJ', 1, 'An impressive work of art.', '2024-03-28', '2024-03-28', '2024-03-28'),
(2, 'iWA-DwAAQBAJ', 2, 'A must-read.', '2024-02-15', '2024-02-15', '2024-02-15'),
(3, 'PGR2AwAAQBAJ', 3, 'Very well written.', '2024-01-10', '2024-01-10', '2024-01-10'),
(4, 'PGR2AwAAQBAJ', 2, 'A deep and thoughtful book.', '2023-11-19', '2023-11-19', '2023-11-19'),
(5, 'itHUEAAAQBAJ', 1, 'I enjoyed this book a lot.', '2024-05-10', '2024-05-10', '2024-05-10'),
(6, 'itHUEAAAQBAJ', 2, 'Not my cup of tea.', '2024-03-24', '2024-03-24', '2024-03-24'),
(7, 'itHUEAAAQBAJ', 3, 'A brilliant piece.', '2024-09-14', '2024-09-14', '2024-09-14');
