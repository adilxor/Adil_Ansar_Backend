import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableFooter from '@material-ui/core/TableFooter';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Paper from '@material-ui/core/Paper';
import CssBaseline from "@material-ui/core/CssBaseline";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core/styles";
import Pagination from "material-ui-flat-pagination";
import Moment from 'react-moment';
import SearchBar from 'material-ui-search-bar';
import { BarLoader } from 'react-spinners';
import TableSortLabel from '@material-ui/core/TableSortLabel';

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: '0px',
    overflowX: 'auto',
  },
  table: {
    minWidth: 700,
  },
  rootMenu: {
    flexGrow: 1,
  },
  row: {
    '&:nth-of-type(odd)': {
      backgroundColor: theme.palette.background.default,
    },
  },
});

const theme = createMuiTheme();
class PresentationList extends Component {

  constructor(props) {
    super(props);
    this.state = {
            presentations: [], isLoading: true, offset: 0, perPage: 10, totalPages: 0, dir:1, searchByTitle: ""
        };
    this.handleSearch = this.handleSearch.bind(this);
    this.handleSort = this.handleSort.bind(this);
  }
  

    ////////////// Pagination ///////////////
    handleClick(offset) {
        const self = this;
        this.setState({ isLoading: true, offset: offset},
            function () {self.fetchPresentations();}
        )
    }


    handleSearch(text){
        const self = this;
        this.setState({isLoading: true, searchByTitle: text, offset: 0 },
            function () {self.fetchPresentations();}
        )

    }

    handleSort(){
      const self = this;
      if (this.state.dir == 1){
        this.setState({isLoading: true, dir: -1},
          function () {self.fetchPresentations();}
        )
      }
      else{
        this.setState({isLoading: true, dir: 1},
          function () {self.fetchPresentations();}
        )
      }
      
    }

    fetchPresentations() {
        const perPage = encodeURIComponent(this.state.perPage);
        const page = encodeURIComponent((this.state.offset/perPage) + 1);
        const direction = encodeURIComponent(this.state.dir);
        const searchByTitle = encodeURIComponent(this.state.searchByTitle);
        const url = `api/v0/presentations?page=${page}&perPage=${perPage}&title=${searchByTitle}&dir=${direction}`
        fetch(url)
          .then(response => response.json())
          .then(data => this.setState({presentations: data.presentationList, isLoading: false, totalPages: data.totalCount}));
    }

  componentDidMount() {
    this.setState({isLoading: true});
    fetch('api/v0/presentations')
      .then(response => response.json())
      .then(data => this.setState({presentations: data.presentationList, isLoading: false, totalPages: data.totalCount}));
  }

  getDirection(direction){
    if (direction === -1){
      return 'desc';
    }
    else{
      return 'asc';
    }
  }

  render() {
    const { classes } = this.props;
    const CustomTableCell = withStyles(theme => ({
      head: {
        backgroundColor: '#2D3037',
        color: theme.palette.common.white,
      },
      body: {
        fontSize: 14,
      },
    }))(TableCell);

    const {presentations, isLoading} = this.state;
    const sortPtr = {
      color: 'white'
    }

    const presentationList = presentations.map(presentation => {
      return (
        <TableRow className={classes.row} key={presentation.id}>
          <CustomTableCell component="th" scope="row" align="left" width="10%">
            <img height="30px" src={presentation.thumbnail}/>
          </CustomTableCell>
          <CustomTableCell component="th" scope="row" align="left" width="15%">{presentation.id}</CustomTableCell>
          <CustomTableCell align="left" width="25%">{presentation.title}</CustomTableCell>
          <CustomTableCell align="left" width="25%">
            <a href={presentation.creator.profileUrl} target="_blank">{presentation.creator.name}</a>
          </CustomTableCell>
          <CustomTableCell align="left" width="25%">
            <Moment format="MMM, D YYYY" withTitle>{presentation.createdAt}</Moment>
          </CustomTableCell>
        </TableRow>
      )
    });
    let presentationListHtml;

    if (isLoading) {
        presentationListHtml =
            <TableBody align='right'>
                <BarLoader
                  sizeUnit={"px"}
                  color={'#000'}
                  loading={this.state.isLoading}
                />
            </TableBody>;
    }
    else{
        if(presentationList.length == 0){
           presentationListHtml =  <TableBody align='right'> No data to show. </TableBody>;
        }
        else{
            presentationListHtml = <TableBody align='right'>{presentationList}</TableBody>;
        }

    }

    return (

      <MuiThemeProvider theme={theme}>
      <div className={classes.rootMenu}>
        <AppBar position="static" color="default">
          <Toolbar>
            <Typography align="left" variant="h6" color="inherit">
              Presentations
            </Typography>
            <div className={classes.rootMenu} />
            <SearchBar
              align="right"
              onRequestSearch={(text) => this.handleSearch(text)}
              onCancelSearch={this.handleSearch.bind(this, "")}
              placeholder = "Search by title..."
              style={{
                width: 300,
              }}
              value = {this.state.searchByTitle}
            />
          </Toolbar>
        </AppBar>
      </div>
        <Paper className={classes.root}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                   <CustomTableCell align="left" width="10%"></CustomTableCell>
                  <CustomTableCell align="left" width="15%">Id</CustomTableCell>
                  <CustomTableCell align="left" width="25%">Title</CustomTableCell>
                  <CustomTableCell align="left" width="25%">Creator</CustomTableCell>
                  <CustomTableCell align="left" width="25%">
                  <TableSortLabel style={{color: 'white'}} active={true} direction={this.getDirection(this.state.dir)} onClick={this.handleSort}>
                      Created At
                    </TableSortLabel>
                  </CustomTableCell>
                </TableRow>
              </TableHead>
              {presentationListHtml}
            </Table>
          </Paper>
          <CssBaseline />
          <Pagination
            align = "center"
            limit={this.state.perPage}
            offset={this.state.offset}
            total={this.state.totalPages}
            onClick={(e, offset) => this.handleClick(offset)}
          />
      </MuiThemeProvider>
    );
  }
}

PresentationList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PresentationList);