import React, { Component } from 'react';
import '../css/App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import PresentationList from './PresentationList';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={() => <PresentationList/>}/>
        </Switch>
      </Router>
    )
  }
}

export default App;