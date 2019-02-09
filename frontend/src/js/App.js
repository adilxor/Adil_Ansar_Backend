import React, { Component } from 'react';
import '../css/App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import PresentationList from './PresentationList';

class App extends Component {
  render() {
    const d = {"row": "presentaion-row", "root":"presentaion-root", "table":"presentaion-table"};
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/presentations' exact={true} component={() => <PresentationList classes={d}/>}/>
        </Switch>
      </Router>
    )
  }
}

export default App;