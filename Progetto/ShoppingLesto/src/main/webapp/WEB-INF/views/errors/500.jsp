<%--
  Created by IntelliJ IDEA.
  User: alessandrogerevini
  Date: 15/11/2018
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<!DOCTYPE html>
<html lang="${language}">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>ShoppingLesto | Error 500 - Webprogramming18</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/error500.css" />

</head>
<body id="page-top">

<%@include file="../parts/_errors.jspf" %>

<div class="container">
    <div class="error">
        <h1>500</h1>
        <h2>error</h2>
        <p>Ruh-roh, something just isn't right... Time to paw through your logs and get down and dirty in your
            stack-trace ;)
            <%= request.getAttribute("javax.servlet.error.message") %>
        </p>
    </div>
    <div class="stack-container">
        <div class="card-container">
            <div class="perspec" style="--spreaddist: 125px; --scaledist: .75; --vertdist: -25px;">
                <div class="card">
                    <div class="writing">
                        <div class="topbar">
                            <div class="red"></div>
                            <div class="yellow"></div>
                            <div class="green"></div>
                        </div>
                        <div class="code">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-container">
            <div class="perspec" style="--spreaddist: 100px; --scaledist: .8; --vertdist: -20px;">
                <div class="card">
                    <div class="writing">
                        <div class="topbar">
                            <div class="red"></div>
                            <div class="yellow"></div>
                            <div class="green"></div>
                        </div>
                        <div class="code">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-container">
            <div class="perspec" style="--spreaddist:75px; --scaledist: .85; --vertdist: -15px;">
                <div class="card">
                    <div class="writing">
                        <div class="topbar">
                            <div class="red"></div>
                            <div class="yellow"></div>
                            <div class="green"></div>
                        </div>
                        <div class="code">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-container">
            <div class="perspec" style="--spreaddist: 50px; --scaledist: .9; --vertdist: -10px;">
                <div class="card">
                    <div class="writing">
                        <div class="topbar">
                            <div class="red"></div>
                            <div class="yellow"></div>
                            <div class="green"></div>
                        </div>
                        <div class="code">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-container">
            <div class="perspec" style="--spreaddist: 25px; --scaledist: .95; --vertdist: -5px;">
                <div class="card">
                    <div class="writing">
                        <div class="topbar">
                            <div class="red"></div>
                            <div class="yellow"></div>
                            <div class="green"></div>
                        </div>
                        <div class="code">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-container">
            <div class="perspec" style="--spreaddist: 0px; --scaledist: 1; --vertdist: 0px;">
                <div class="card">
                    <div class="writing">
                        <div class="topbar">
                            <div class="red"></div>
                            <div class="yellow"></div>
                            <div class="green"></div>
                        </div>
                        <div class="code">
                            <ul>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    const stackContainer = document.querySelector('.stack-container');
    const cardNodes = document.querySelectorAll('.card-container');
    const perspecNodes = document.querySelectorAll('.perspec');
    const perspec = document.querySelector('.perspec');
    const card = document.querySelector('.card');

    let counter = stackContainer.children.length;

    //function to generate random number
    function randomIntFromInterval(min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }

    //after tilt animation, fire the explode animation
    card.addEventListener('animationend', function () {
        perspecNodes.forEach(function (elem, index) {
            elem.classList.add('explode');
        });
    });

    //after explode animation do a bunch of stuff
    perspec.addEventListener('animationend', function (e) {
        if (e.animationName === 'explode') {
            cardNodes.forEach(function (elem, index) {

                //add hover animation class
                elem.classList.add('pokeup');

                //add event listner to throw card on click
                elem.addEventListener('click', function () {
                    let updown = [800, -800]
                    let randomY = updown[Math.floor(Math.random() * updown.length)];
                    let randomX = Math.floor(Math.random() * 1000) - 1000;
                    elem.style.transform = `translate(${randomX}px, ${randomY}px) rotate(-540deg)`
                    elem.style.transition = "transform 1s ease, opacity 2s";
                    elem.style.opacity = "0";
                    counter--;
                    if (counter === 0) {
                        stackContainer.style.width = "0";
                        stackContainer.style.height = "0";
                    }
                });

                //generate random number of lines of code between 4 and 10 and add to each card
                let numLines = randomIntFromInterval(5, 10);

                //loop through the lines and add them to the DOM
                for (let index = 0; index < numLines; index++) {
                    let lineLength = randomIntFromInterval(25, 97);
                    var node = document.createElement("li");
                    node.classList.add('node-' + index);
                    elem.querySelector('.code ul').appendChild(node).setAttribute('style', '--linelength: ' + lineLength + '%;');

                    //draw lines of code 1 by 1
                    if (index == 0) {
                        elem.querySelector('.code ul .node-' + index).classList.add('writeLine');
                    } else {
                        elem.querySelector('.code ul .node-' + (index - 1)).addEventListener('animationend', function (e) {
                            elem.querySelector('.code ul .node-' + index).classList.add('writeLine');
                        });
                    }
                }
            });
        }
    });
</script>
</body>

</html>