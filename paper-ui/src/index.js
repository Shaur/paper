import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import {Route, BrowserRouter } from "react-router-dom";
import App from './App';
import Reader from "./Reader";
import Login from "./Login"

ReactDOM.render((
    <BrowserRouter>
        <Route exact path='/login' component={Login}/>
        <Route exact path='/' component={App}/>
        <Route path='/read/:series/:number' component={Reader}/>
    </BrowserRouter>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
