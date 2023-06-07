import React,{ useState, useLayoutEffect } from 'react';
import {Link, useParams} from 'react-router-dom';
import {withGetFetch} from '../../../fetches/FetchChangeOneLetter';
import Loader from '../../../pages/Loader';
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
import Methods, {range} from '../../../common/Methods';

const sizes = {
    'small': 5,
    'medium': 10,
    'large': 15
};

const sizeByLabel = {}

Object.keys(sizes).forEach(key =>sizeByLabel[sizes[key]]=key)

const ListCrossWordJob = (props)=> {
    const {size, index} = useParams();
    let [data, setData] = useState({})
    const rowsPerPage = sizes[size]|'medium';
    const page = index|0;

    console.log('size = ' + size + ' index = ' + page)

    withGetFetch(
        '/api/games/crossword/jobs/'+page+'/'+sizes[size],
        {},
        setData,
        ()=>{return true}
    );

    const handleChangePage = (
        event,
        newPage
      ) => {
        data = {}
        props.history.push('/crossword/jobs/open/pages/'+size+'/'+newPage)
        withGetFetch(
            '/api/games/crossword/jobs/'+newPage+'/'+sizes[size],
            {},
            setData,
            ()=>{return true}
        );
      };

      const handleChangeRowsPerPage = (
        event
      ) => {
        data = {}
        props.history.push('/crossword/jobs/open/pages/'+sizeByLabel[parseInt(event.target.value, 10)]+'/0')
        withGetFetch(
            '/api/games/crossword/jobs/'+event.target.value+'/0',
            {},
            setData,
            ()=>{return true}
        );
      };

    return (
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="center">Words</TableCell>
                <TableCell align="center">Description</TableCell>
                <TableCell align="center">Variety</TableCell>
                <TableCell align="center">Status</TableCell>
                <TableCell align="center">Message</TableCell>
                <TableCell align="center">Open</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
            {
                !!data.content?
                data.content
                  .map((row) => (
                          <>
                            <TableRow
                              sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                              <TableCell align="center" component="th" scope="row">
                                {row.words[0].word}
                              </TableCell>
                              <TableCell align="center" component="th" scope="row">
                                  {row.words[0].desc}
                                </TableCell>
                              <TableCell rowSpan={row.words.length} align="center" valign="middle">
                                {row.options}
                              </TableCell>
                              <TableCell  rowSpan={row.words.length} align="center" valign="middle">
                                  {row.status}
                                </TableCell>
                                <TableCell rowSpan={row.words.length} align="center" valign="middle">
                                  {row.message}
                                </TableCell>
                              <TableCell rowSpan={row.words.length} align="center" valign="middle">

                                <Link to={`/crossword/open/run/${row.id}`} className={"btn btn-primary"}>Open</Link>

                              </TableCell>
                            </TableRow>
                            {
                                range(1, row.words.length - 1)
                                    .map(index=> (
                                        <TableRow>
                                            <TableCell align="center" component="th" scope="row">
                                                {row.words[index].word}
                                              </TableCell>
                                              <TableCell align="center" component="th" scope="row">
                                                  {row.words[index].desc}
                                                </TableCell>
                                        </TableRow>
                                    ))
                            }
                        </>
                    )
                ):
                <>
                </>
            }
            </TableBody>
            {
                !!data.pageable?
                (
                    <TableFooter>
                      <TableRow>
                        <TablePagination
                          rowsPerPageOptions={Object.keys(sizes).map((key)=>{
                            return {label: new Methods().toProperCase(key), value: sizes[key]};
                          })}
                          colSpan={6}
                          count={data.totalElements}
                          rowsPerPage={data.pageable.pageSize}
                          page={data.pageable.pageNumber}
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
                ):
                (
                    <>

                    </>
                )
            }

          </Table>
        </TableContainer>
    )
}

function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const nextPage = page + 1;
  const lastPage = Math.ceil(count / rowsPerPage) - 1;

  const firstUrl = '/change-one-letter/open/pages/'+sizeByLabel[rowsPerPage]+'/0'
  const prevUrl = '/change-one-letter/open/pages/'+sizeByLabel[rowsPerPage]+'/'+(page-1)
  const nextUrl = '/change-one-letter/open/pages/'+sizeByLabel[rowsPerPage]+'/'+nextPage
  const lastUrl = '/change-one-letter/open/pages/'+sizeByLabel[rowsPerPage]+'/'+lastPage

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
                    onClick={onPageChange}
                  >
                    <FirstPageIcon />
                  </Link>

                  <Link
                    to={prevUrl}
                    aria-label="previous page"
                    onClick={onPageChange}
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
                    onClick={onPageChange}
                  >
                    <KeyboardArrowRight />
                  </Link>
                  <Link
                      to={lastUrl}
                      aria-label="last page"
                      onClick={onPageChange}
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


export default ListCrossWordJob;