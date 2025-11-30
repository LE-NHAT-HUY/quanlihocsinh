<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            background: linear-gradient(to bottom, #87CEEB, #00BFFF);
            font-family: 'Roboto', sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .login-container {
            background: rgba(0, 51, 102, 0.95); /* xanh nước biển */
            padding: 40px 50px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.3);
            text-align: center;
            width: 400px;
            color: white; /* chữ mặc định trắng */
        }

        .login-logo {
            width: 200px;
            height: auto;
            margin-bottom: 20px;
        }

        h2 {
            margin-bottom: 25px;
            font-size: 28px;
            font-weight: 700;
            color: #ffffff;
        }

        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            font-weight: 500;
            color: #ffffff;
        }

        input[type="text"], input[type="password"], button {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border-radius: 6px;
            font-size: 16px;
            box-sizing: border-box;
        }

        input[type="text"], input[type="password"] {
            border: 1px solid #ffffff;
            background-color: rgba(255,255,255,0.1);
            color: white;
        }

        input[type="text"]::placeholder, input[type="password"]::placeholder {
            color: #cce0ff;
        }

        button {
            background-color: #0066cc; /* xanh đậm hơn cho nút */
            color: white;
            font-weight: 700;
            border: none;
            cursor: pointer;
            transition: 0.3s;
        }

        button:hover {
            background-color: #004d99;
        }

        .below-text {
            margin-top: 10px;
            font-size: 14px;
            color: #cce0ff;
        }

        .error-message {
            margin-top: 15px;
            color: #ff6666;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <img src="${pageContext.request.contextPath}/assets/img/logo-truong-anh-son-1.png" alt="Logo" class="login-logo">
        <h2>Đăng nhập</h2>
        <form method="post" action="login">
            <label>Tên đăng nhập</label>
            <input type="text" name="username" placeholder="Nhập tên đăng nhập" required>

            <label>Mật khẩu</label>
            <input type="password" name="password" placeholder="Nhập mật khẩu" required>

            <button type="submit">Đăng nhập</button>
        </form>

        <div class="below-text">
            Vui lòng điền đầy đủ thông tin để đăng nhập
        </div>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </div>
</body>
</html>
