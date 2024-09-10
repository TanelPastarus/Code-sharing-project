<html lang="en">
<head>
    <style>
    <#include "styles.css">
    </style>
    <title>Create</title>
</head>
<body>
<br>
<textarea id="code_snippet">Add new code</textarea>
<br>
<p>Time restriction:</p><input id="time_restriction" type="text" value="0"/>
<br>
<p>Maximum views:</p><input id="views_restriction" type="text" value="0"/>
<br>
<button id="send_snippet" type="submit" onclick="send()">Submit</button>
<br>
</body>
<script>
    function send() {
        let object = {
            "code": document.getElementById("code_snippet").value,
            "time": document.getElementById("time_restriction").value,
            "views": document.getElementById("views_restriction").value
        };

        let json = JSON.stringify(object);
        let xhr = new XMLHttpRequest();
        xhr.open("POST", '/api/code/new', false)
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(json);
        if (xhr.status === 200) {
            alert("Success!");
        }
    }
</script>
</html>