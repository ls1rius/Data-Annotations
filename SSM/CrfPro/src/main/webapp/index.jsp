<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>test</title>
</head>
<style>
    body{
        width:100%;
        height: 100%;
    }
    .content{
        width: 80%;
        height: 300px;
        margin: auto;
    }
    textarea{
        width: 100%;
        height: 90%;
        display: block;
        font-size: 20px;
        box-sizing: border-box;
    }
    span{
        height: 10%;
        padding: 0;
        margin: 0;
        display: flex;
        text-align: center;
        align-items: center;
        vertical-align: middle;
    }
    p{
        margin: 0;
        padding: 0;
    }
    #btn{
        display: block;
        margin: auto;
        width: 90px;
        height: 30px;
        font-size: 20px;
        margin-top: 20px;
    }
</style>
<script>
    function dealData() {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                console.log(xmlhttp.responseText);
                var obj = JSON.parse(xmlhttp.responseText);
                document.getElementById("op").value = obj.result;
            }
        }
        xmlhttp.open("POST", "test/dealData", true);
        xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xmlhttp.send("data=" + document.getElementById("ip").value);
        console.log("data=" + document.getElementById("ip").value);
    }
</script>
<body>
<div>
    <div class="content">
            <span>
                <p>INPUT:</p>
            </span>
        <textarea id="ip"></textarea>
    </div>
    <div class="content">
            <span>
                <p>OUTPUT:</p>
            </span>
        <textarea id="op"></textarea>
    </div>
    <button id = "btn" type="button" onclick="dealData()">START</button>
</div>
</body>
</html>
