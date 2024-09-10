<!DOCTYPE html>
<#setting datetime_format="iso">
<html lang="en">
<head>
    <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <style>
        <#include "stylesLatest.css">
    </style>
    <title>Latest</title>
</head>
<body>
<#list codeSnippets as codeSnippet>
    <#assign aDateTime = codeSnippet.date?datetime>
    <div id = code_box>
    <span>${aDateTime?string["yyyy/MM/dd HH:mm:ss"]}</span>
    <pre><code>${codeSnippet.code}</code></pre>
    </div>
</#list>
</body>
</html>