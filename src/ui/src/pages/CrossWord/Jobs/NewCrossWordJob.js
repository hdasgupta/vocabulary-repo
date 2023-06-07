import React, {useState} from 'react';
import {Link, useParams} from 'react-router-dom';
import {withPostBodyFetch} from '../../../fetches/FetchChangeOneLetter';
import { faTrash, faPlus, faAngleUp, faAngleDown} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Loader from '../../../pages/Loader';
import CrossWordJob from '../../../pages/CrossWord/Jobs/CrossWordJob';

const newWord = () =>{
    return {
        word: "",
        desc: ""
    }
}

const NewGuessTheWord = (props) => {
    const [words, setWords] = useState({
        wordByDesc: [
            newWord()
        ],
        cyclicEnabled: false,
        adjacentEnabled: false
    })
    const [job, setJob] = useState(undefined)

    const update= (job) => {
         job()
         setWords(words)
    }

    const changeCyclic = () => update(()=> {
        words.cyclicEnabled = !words.cyclicEnabled
    })

    const changeAdjacent = () => update(()=> {
        words.adjacentEnabled = !words.adjacentEnabled
    })

    const updateWord = (index, text) => update(() => {
         words.wordByDesc[index].word = text
    })

    const updateDesc = (index, text) => update(() => {
         words.wordByDesc[index].desc = text
    })

    const remove = (index) => update(() => {
        words.wordByDesc.splice(index, 1)
    })


    const moveUp = (index) => update(() => {
        const item = words.wordByDesc[index-1]
        words.wordByDesc.splice(index-1, 1)
        words.wordByDesc.splice(index, 0, item);
    })

    const moveDown = (index) => update(() => {
        const item = words.wordByDesc[index]
        words.wordByDesc.splice(index, 1)
        words.wordByDesc.splice(index+1, 0, item);
    })

    const add = (index) => update(() => {
        words.wordByDesc.splice(index+1, 0, newWord());
    })

    const submit = () => {
        withPostBodyFetch(
            '/api/games/crossword/jobs/create',
            words,
            setJob,
            ()=>{return true}
        )
    }

    if(!job) {
        return (
            <Form onSubmit={(e)=>submit()}>
                <table>
                    <tbody>
                        <tr>
                            <td colSpan="3" align="center">
                                <Form.Check type="checkbox" value={words.cyclicEnabled} onClick={(e)=>changeCyclic()}>
                                    <Form.Check.Input type="checkbox" isValid />
                                    <Form.Check.Label>{"Enable Cyclic"}</Form.Check.Label>
                                </Form.Check>
                            </td>
                            <td colSpan="3" align="center">
                                <Form.Check type="checkbox" value={words.adjacentEnabled} onClick={(e)=>changeAdjacent()}>
                                    <Form.Check.Input type="checkbox" isValid />
                                    <Form.Check.Label>{"Enable Cyclic"}</Form.Check.Label>
                                </Form.Check>
                            </td>
                        </tr>
                        <tr>
                            <td colSpan="6" align="center">
                                <Button variant="primary" onClick={(e) => add(-1)}>
                                    <FontAwesomeIcon className="icon" icon={faPlus}/>
                                </Button>
                            </td>
                        </tr>
                    {
                        words.wordByDesc.map((wordByDesc, index)=>{
                            return (
                                <tr>
                                    <td>
                                        <Form.Control type="text" value={wordByDesc.word} placeholder="Enter word..." onKeyPress={(e)=>updateWord(index, e.target.value)} />
                                    </td>
                                    <td>
                                        <Form.Control type="text" value={wordByDesc.desc} placeholder="Enter Description..." onKeyPress={(e)=>updateDesc(index, e.target.value)} />
                                    </td>
                                    <td>
                                        <Button variant="primary" onClick={(e) => remove(index)}>
                                            <FontAwesomeIcon className="icon" icon={faTrash}/>
                                        </Button>
                                    </td>
                                    <td>
                                        <Button variant="primary" onClick={(e) => moveUp(index)}>
                                            <FontAwesomeIcon className="icon" icon={faTrash}/>
                                        </Button>
                                    </td>
                                    <td>
                                        <Button variant="primary" onClick={(e) => moveDown(index)}>
                                            <FontAwesomeIcon className="icon" icon={faTrash}/>
                                        </Button>
                                    </td>
                                    <td>
                                        <Button variant="primary" onClick={(e) => add(index)}>
                                            <FontAwesomeIcon className="icon" icon={faPlus}/>
                                        </Button>
                                    </td>
                                </tr>
                            )
                        })
                    }
                        <tr>
                            <td colSpan="6" align="center">
                                <Button variant="primary" onClick={(e) => add(-1)}>
                                    Submit
                                </Button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </Form>
        )
    } else {
        return (
            <CrossWordJob {...job}/>
        )
    }

};

export default NewGuessTheWord;