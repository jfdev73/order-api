INSERT INTO usuario (user_name, password) VALUES('fer','1234');

INSERT INTO product(NAME, PRICE) VALUES ('Product 1', 200),
('Product 2', 200),('Product 3', 200), ('Product 4', 200),
('Product 5', 200),('Product 6', 200), ('Product 7', 200),
('Product 8', 200), ('Product 9', 200),('Product 10', 200);


INSERT INTO orden(reg_date, total) VALUES (now(),400);

INSERT INTO order_line(orden,product, price, quantity, total ) VALUES (1,1,100,1,100),
(1,2,200,1,200), (1,3,300,1,300),(1,4,400,1,400),(1,5,500,1,500);

INSERT INTO orden(reg_date, total) VALUES (now(),4000);

INSERT INTO order_line(orden,product, price, quantity, total ) VALUES (2,6,600,1,100),
(2,7,700,1,700), (2,8,800,1,800),(2,9,900,1,900),(2,10,1000,1,1000);