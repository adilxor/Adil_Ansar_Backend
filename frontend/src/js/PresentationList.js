import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableFooter from '@material-ui/core/TableFooter';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import CssBaseline from "@material-ui/core/CssBaseline";
import { createMuiTheme, MuiThemeProvider } from "@material-ui/core/styles";
import Pagination from "material-ui-flat-pagination";
import Moment from 'react-moment';
import SearchBar from 'material-ui-search-bar';
import { BarLoader } from 'react-spinners';


const theme = createMuiTheme();
class PresentationList extends Component {

  constructor(props) {
    super(props);
    this.state = {
            presentations: [], isLoading: true, offset: 0, perPage: 10, totalPages: 100, dir:1, searchByTitle: ""
        };
    this.handleSearch = this.handleSearch.bind(this);
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
        this.setState({isLoading: true, searchByTitle: text},
            function () {self.fetchPresentations();}
        )

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

  render() {
    const { classes } = this.props;
    const CustomTableCell = withStyles(theme => ({
      head: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
      },
      body: {
        fontSize: 14,
      },
    }))(TableCell);

    const styles = theme => ({
      root: {
        width: '100%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
      },
      table: {
        minWidth: 700,
      },
      row: {
        '&:nth-of-type(odd)': {
          backgroundColor: theme.palette.background.default,
        },
      },
    });

    const {presentations, isLoading} = this.state;

    const presentationList = presentations.map(presentation => {
      return (
        <TableRow className={classes.row} key={presentation.id}>
          <CustomTableCell component="th" scope="row" align="left">{presentation.id}</CustomTableCell>
          <CustomTableCell align="left">{presentation.title}</CustomTableCell>
          <CustomTableCell align="left">
            <a href={presentation.creator.profileUrl} target="_blank">{presentation.creator.name}</a>
          </CustomTableCell>
          <CustomTableCell align="left">
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
      <SearchBar
            onRequestSearch={(text) => this.handleSearch(text)}
            onCancelSearch={this.handleSearch.bind(this, "")}
            placeholder = "Search by title..."
            style={{
              margin: '5px auto',
              width: 200,
            }}
            value = {this.state.searchByTitle}
          />
        <Paper className={classes.root}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                  <CustomTableCell align="left">Id</CustomTableCell>
                  <CustomTableCell align="left">Title</CustomTableCell>
                  <CustomTableCell align="left">Creator</CustomTableCell>
                  <CustomTableCell align="left">Created At</CustomTableCell>
                </TableRow>
              </TableHead>
                {presentationListHtml}
              <TableFooter>
                  <CssBaseline />
                  <Pagination
                    limit={this.state.perPage}
                    offset={this.state.offset}
                    total={this.state.totalPages}
                    onClick={(e, offset) => this.handleClick(offset)}
                  />
                </TableFooter>
            </Table>
          </Paper>
      </MuiThemeProvider>
    );
  }
}

PresentationList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default PresentationList;