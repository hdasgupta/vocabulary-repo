import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import {withGetFetch} from '../../../fetches/FetchChangeOneLetter';
import { faTrash, faPlus, faAngleUp, faAngleDown} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Loader from '../../../pages/Loader';
import CrossWordJob from '../../../pages/CrossWord/Jobs/CrossWordJob';

const OpenCrossWordJob = (props) => {
    const {jobId} = useParams();
    const [job, setJob] = useState(undefined)

    withGetFetch(
        '/api/games/crossword/jobs/get/'+jobId,
        {},
        setJob,
        ()=>{return true}
    );

    if(!!job) {
        return (
            <CrossWordJob {...job} />
        )
    } else {
        return (
            <Loader />
        )
    }
}

export default OpenCrossWordJob;