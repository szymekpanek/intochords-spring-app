-- Dodanie użytkowników
INSERT INTO t_user (user_id, class_id, username, surname, login, password, role) VALUES
(NULL, NULL, 'Jan', 'Kowalski', 'jkowalski', 'password123', 'ADMIN'),
(NULL, NULL, 'Anna', 'Nowak', 'anowak', 'password123', 'TEACHER'),
(NULL, NULL, 'Piotr', 'Wiśniewski', 'pwisniewski', 'password123', 'USER');

-- Dodanie klas
INSERT INTO t_class (class_id, class_name, creator_id) VALUES
(NULL, 'Klasa Muzyczna A', 2),
(NULL, 'Klasa Muzyczna B', 2);

-- Przypisanie użytkowników do klas
UPDATE t_user SET class_id = 1 WHERE user_id = 3; -- Piotr do Klasy A

-- Dodanie gier
INSERT INTO t_games (game_id, game_name) VALUES
(1, 'IntervalGame'),
(2, 'ChordsGame');

-- Dodanie statystyk gier
INSERT INTO t_game_stats (game_stats_id, user_id, game_id, game_date, correct_answer, incorrect_answer) VALUES
(1, 3, 1, '2024-02-03 10:15:00', 8, 2),
(2, 3, 2, '2024-02-03 11:30:00', 5, 5);

-- Dodanie przypisania użytkowników do klas w tabeli pośredniej
INSERT INTO t_user_class (user_id, class_id) VALUES
(3, 1);
