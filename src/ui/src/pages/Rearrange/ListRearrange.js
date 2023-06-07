import React,{ useState, useLayoutEffect } from 'react';
import {Link, useParams} from 'react-router-dom';
import useFetch from '../../fetches/FetchChangeOneLetter';
import Loader from '../../pages/Loader';
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableFooter from '@mui/material/TableFooter';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import Methods from '../../common/Methods';

const sizes = {
    'small': 5,
    'medium': 10,
    'large': 15
};

const sizeByLabel = {}

Object.keys(sizes).forEach(key =>sizeByLabel[sizes[key]]=key)

const ListChangeOneLetter = (props)=> {
    const {size, index} = useParams();
    const [data, setData] = useState([])
    const [isLoading, setIsLoading] = useState(true);
    const rowsPerPage = sizes[size]|'medium';
    const page = index|0;

    console.log('size = ' + size + ' index = ' + page)

    useFetch(
        '/api/games/rearrange/',
        {},
        setData,
        setIsLoading,
        isLoading,
        ()=>{return true}
    );

    const handleChangePage = (
        event,
        newPage
      ) => {
        props.history.push('/rearrange/open/pages/'+size+'/'+newPage)
      };

      const handleChangeRowsPerPage = (
        event
      ) => {
        props.history.push('/rearrange/open/pages/'+sizeByLabel[parseInt(event.target.value, 10)]+'/0')
      };

    if(!!data && !isLoading) {

        return (
            <TableContainer component={Paper}>
              <Table sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                  <TableRow>
                    <TableCell align="center">Chars</TableCell>
                    <TableCell align="center">Word Size</TableCell>
                    <TableCell align="center">Is Alive?</TableCell>
                    <TableCell align="center">Open</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {data.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row) => (
                    <TableRow
                      sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                      <TableCell align="center" component="th" scope="row">
                        {row.chars.join(", ")}
                      </TableCell>
                      <TableCell align="center" component="th" scope="row">
                          {row.chars.length}
                        </TableCell>
                      <TableCell align="center" style={{color:row.isClosed ?'red':'green'}}>
                        {row.isClosed ? '👎' : '👍'}
                      </TableCell>
                      <TableCell align="center">

                        <Link to={`/rearrange/open/run/${row.id}`} className={"btn btn-primary"}>Open</Link>

                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
                <TableFooter>
                  <TableRow>
                    <TablePagination
                      rowsPerPageOptions={Object.keys(sizes).map((key)=>{
                        return {label: new Methods().toProperCase(key), value: sizes[key]};
                      })}
                      colSpan={3}
                      count={data.length}
                      rowsPerPage={rowsPerPage}
                      page={page}
                      SelectProps={{
                        inputProps: {
                          'aria-label': 'rows per page',
                        },
                        native: true,
                      }}
                      onPageChange={handleChangePage}
                      onRowsPerPageChange={handleChangeRowsPerPage}
                      ActionsComponent={TablePaginationActions}
                    />
                  </TableRow>
                </TableFooter>
              </Table>
            </TableContainer>
        )
    }
    else
        return (<Loader />)
}

function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const nextPage = page + 1;
  const lastPage = Math.ceil(count / rowsPerPage) - 1;

  const firstUrl = '/rearrange/open/pages/'+sizeByLabel[rowsPerPage]+'/0'
  const prevUrl = '/rearrange/open/pages/'+sizeByLabel[rowsPerPage]+'/'+(page-1)
  const nextUrl = '/rearrange/open/pages/'+sizeByLabel[rowsPerPage]+'/'+nextPage
  const lastUrl = '/rearrange/open/pages/'+sizeByLabel[rowsPerPage]+'/'+lastPage

  console.log('next = ' + nextPage + ', last = ' + lastPage)

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
        {
            page > 0?
            (
            <>
                <Link
                    to={firstUrl}
                    aria-label="first page"
                  >
                    <FirstPageIcon />
                  </Link>

                  <Link
                    to={prevUrl}
                    aria-label="previous page"
                  >
                    <KeyboardArrowLeft />
                  </Link>
            </>
            ):
            (
            <>
                <FirstPageIcon />
                <KeyboardArrowLeft />
            </>
            )
        }


      {
        page < lastPage ?
        (
            <>
                <Link
                    to={nextUrl}
                    aria-label="next page"
                  >
                    <KeyboardArrowRight />
                  </Link>
                  <Link
                      to={lastUrl}
                      aria-label="last page"
                    >
                      <LastPageIcon />
                    </Link>
            </>
          )
         :
         (
         <>
            <KeyboardArrowRight />
            <LastPageIcon />
         </>
         )

      }


    </Box>
  );
}


export default ListChangeOneLetter;