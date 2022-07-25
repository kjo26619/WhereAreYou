import React from 'react';
import ReactDOM from 'react-dom';
import Routes from './Routes';

import './style.css';

// Redux
import {Provider} from 'react-redux';
import {createStore, applyMiddleware} from 'redux';
import reducers from './reducers';
import thunk from 'redux-thunk';

const store = createStore(reducers, applyMiddleware(thunk));

ReactDOM.render(
  <React.StrictMode>
    <Provider store = {store}>
      <Routes />
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);