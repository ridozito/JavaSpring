insert into menu(parent_id,url,name,order_index) values (0,'/product','Sản phẩm',1),
(0,'/stock','Kho',2),
(0,'/management','Quản lý',3),
(1,'/product-info/list','Danh sách sản phẩm',2),
(1,'/category/list','Danh sách category',1),
(1,'/category/edit','Sửa',-1),
(1,'/category/view','Xem',-1),
(1,'/category/add','Thêm mới',-1),
(1,'/category/save','Lưu',-1),
(1,'/category/delete','Xoá',-1),

(2,'/goods-recept/list','Danh sách nhập kho',1),
(2,'/goods-issue/list','Danh sách xuất kho',2),
(2,'/product-in-stock/list','Sản phẩm trong kho',3),
(2,'/history','Lịch sử kho',4),

(3,'/user/list','Danh sách user',1),
(3,'/menu/list','Danh sách menu',1),
(3,'/role/list','Danh sách quyền',1)
