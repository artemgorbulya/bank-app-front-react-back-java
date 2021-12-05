import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App/App';
import {BrowserRouter} from "react-router-dom";
import CssBaseline from '@material-ui/core/CssBaseline';


ReactDOM.render(
    <React.StrictMode>
        <BrowserRouter>
            <CssBaseline/>
            <App/>
        </BrowserRouter>
    </React.StrictMode>,
    document.getElementById('root')
);

