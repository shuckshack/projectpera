<html>
<head><title>Welcome!</title></head>
<body>
    <h1>Hello ${name}</h1>
    <form action="/favourite_fruit" method="POST">
        <p>What is your favourite fruit?</p>
        <#list fruits as fruit>
            <p>
                <input type="radio" name="fruit" value="${fruit}">${fruit}</input>
            </p>
        </#list>
        <input type="submit" value="Submit"/>
    </form>
</body>
</html>