import React,{ useState, useLayoutEffect } from 'react';
import {Link, useParams} from 'react-router-dom';
import { faAngleLeft, faAngleRight} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {withGetFetch} from '../../../fetches/FetchChangeOneLetter';
import CrossWordBoard from '../../../pages/CrossWord/CrossWordBoard';
import Loader from '../../../pages/Loader';

const ListCrossWordJobBoards = (props) => {
    const {jobId, index} = useParams()
    const [boards, setBoards] = useState(undefined)

    withGetFetch(
        '/api/games/crossword/boards/by-job/'+jobId+'/'+index,
        {},
        setBoards,
        ()=>{return true}
    )

    if(!!boards) {
        return (
            <table>
                <tbody>
                    <tr>
                        <td valign="middle">
                            <Link to={'/crossword/jobs/boards/'+jobId+'/'+(index-1)} className="btn btn-primary" disabled={boards.first}>
                                <FontAwesomeIcon className="icon" icon={faAngleLeft}/>
                            </Link>
                        </td>
                        <td>
                            <CrossWordBoard {...boards} readonly={true} />
                        </td>
                        <td valign="middle">
                            <Link to={'/crossword/jobs/boards/'+jobId+'/'+(index+1)} className="btn btn-primary" disabled={boards.last}>
                                <FontAwesomeIcon className="icon" icon={faAngleRight}/>
                            </Link>
                        </td>
                    </tr>
                </tbody>
            </table>
        )
    } else {
        return (
            <Loader />
        )
    }

}

export default ListCrossWordJobBoards