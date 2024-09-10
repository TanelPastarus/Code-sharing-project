<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet"
          href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <style>
        <#include "stylesCode.css">
    </style>
    <title>Code</title>
</head>
<body>
<pre id = "code_snippet" ><code>${codeBody}</code></pre>
<span id = "load_date" >${date}</span>
<#if time gte 0>
<br>
<span id="time_restriction"> The code will be available for <b>${time}</b> seconds</span>
</#if>
<#if views gte 0>
<br>
<span id="views_restriction"> <b>${views}</b> more views allowed</span>
</#if>
</body>
</html>