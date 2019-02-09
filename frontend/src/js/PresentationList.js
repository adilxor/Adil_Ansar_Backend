import React, {Component} from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';


class PresentationList extends Component {

  constructor(props) {
    super(props);
    this.state = {presentations: [], isLoading: true};
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/v0/presentations')
      .then(response => response.json())
      .then(data => this.setState({presentations: data, isLoading: false}));
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

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const presentationList = presentations.map(presentation => {
      return (
        <TableRow className={classes.row} key={presentation.id}>
          <CustomTableCell component="th" scope="row">{presentation.id}</CustomTableCell>
          <CustomTableCell align="right">{presentation.title}</CustomTableCell>
          <CustomTableCell align="right">
            <a href={presentation.creator.profileUrl} target="_blank">{presentation.creator.name}</a>
          </CustomTableCell>
          <CustomTableCell align="right">{presentation.createdAt}</CustomTableCell>
        </TableRow>
      )
    });

    return (
      <div>
        <Paper className={classes.root}>
            <Table className={classes.table}>
              <TableHead>
                <TableRow>
                  <CustomTableCell>Id</CustomTableCell>
                  <CustomTableCell align="right">Title</CustomTableCell>
                  <CustomTableCell align="right">Creator</CustomTableCell>
                  <CustomTableCell align="right">Created At</CustomTableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {presentationList}
              </TableBody>
            </Table>
          </Paper>
      </div>
    );
  }
}

PresentationList.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default PresentationList;