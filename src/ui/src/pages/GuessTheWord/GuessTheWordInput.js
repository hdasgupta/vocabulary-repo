import React, {useState} from 'react';
import { faBars, faArrowLeft} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Button from 'react-bootstrap/Button';
import {range} from '../../common/Methods'
import {withPostFetch} from '../../fetches/FetchChangeOneLetter'
import '../../pages/GuessTheWord/GuessTheWordInput.css';

const keyboard = [
    [
        {'text':'A', 'tab':1, 'type':'_letter'}, 
        {'text':'B', 'tab':1, 'type':'_letter'}, 
        {'text':'C', 'tab':1, 'type':'_letter'}, 
        {'text':'D', 'tab':1, 'type':'_letter'}, 
        {'text':'E', 'tab':1, 'type':'_letter'}
    ],
    [
        {'text':'F', 'tab':1, 'type':'_letter'},
        {'text':'G', 'tab':1, 'type':'_letter'},
        {'text':'H', 'tab':1, 'type':'_letter'}, 
        {'text':'I', 'tab':1, 'type':'_letter'}, 
        {'text':'J', 'tab':1, 'type':'_letter'}
    ],
    [
        {'text':'K', 'tab':1, 'type':'_letter'},
        {'text':'L', 'tab':1, 'type':'_letter'},
        {'text':'M', 'tab':1, 'type':'_letter'},
        {'text':'N', 'tab':1, 'type':'_letter'}, 
        {'text':'O', 'tab':1, 'type':'_letter'}
    ],
    [
        {'text':'P', 'tab':1, 'type':'_letter'},
        {'text':'Q', 'tab':1, 'type':'_letter'},
        {'text':'R', 'tab':1, 'type':'_letter'},
        {'text':'S', 'tab':1, 'type':'_letter'},
        {'text':'T', 'tab':1, 'type':'_letter'}
    ],
    [
        {'text':'U', 'tab':1, 'type':'_letter'},
        {'text':'V', 'tab':1, 'type':'_letter'},
        {'text':'W', 'tab':1, 'type':'_letter'},
        {'text':'X', 'tab':1, 'type':'_letter'},
        {'text':'Y', 'tab':1, 'type':'_letter'}
    ],
    [
        {'text':'Z', 'tab':1, 'type':'_letter'},
        {'icon':'arrowLeft', 'tab':1, 'type':'_delete'},
        {'text':'Submit', 'tab':3, 'type':'_submit'}
    ]
]

const GuessTheWordInput = (props)=> {
    const {onSelect, wordLength} = props
    const [index, setIndex] = useState(0);
    let [validate, setValidate] = useState(false)
    const initChars = ()=>{return range(0, wordLength-1).map(i=>'')}
    let [chars, setChars] = useState(initChars())

    console.log('chars = '+chars+', wordLength = '+wordLength+', onSelect = '+onSelect)
    
    const _letter = (text) => {
        if(index < wordLength) {
            chars[index] = text;
            if((index + 1) >= props.wordLength) {
                withPostFetch(
                    '/api/games/guess-the-word/validate/'+props.id+'/'+chars.join(''),
                    {},
                    setValidate,
                    ()=>{return true}
                )
            }
            setIndex(index + 1)
        }
    }
    
    const _delete = () => {
        if(index > 0) {
            chars[index - 1] = '';
            validate = false;
            setIndex(index - 1);
        }
    }

    const _submit = () => {
        if(validate) {
            validate = false;
            onSelect(chars.join(''))
            range(0, chars.length-1).forEach((i) => chars[i] = '');
            setIndex(0)
        }
    }

    const actions = {_letter, _delete, _submit}

    return (
    <table>
        <tbody>
            <tr>
            {
                !!chars?
                chars.map((char, i)=> (
                    <td align="center" valign="middle" className={i==index?'Selected':'NotSelected'}>
                    {
                        char
                    }
                    </td>
                )):
                (
                    <>
                    </>
                )
            }
            </tr>
            <tr>
                <td colSpan={wordLength} align="center" valign="top">
                    <table>
                        <tbody>
                        {
                            keyboard.map((keys=>{
                                return (
                                    <tr>
                                    {
                                        keys.map((key)=>{
                                            return (
                                                <td colSpan={key.tab} align="center">
                                                    <Button variant="primary" onClick={(e)=>actions[key.type](key.text)} disabled={key.type=='_submit' && !validate}>
                                                    {
                                                        key.type=='_delete'?
                                                        (
                                                            <FontAwesomeIcon  className="icon" icon={faArrowLeft} />
                                                        ):
                                                        key.text
                                                    }
                                                    </Button>
                                                </td>
                                            )
                                        })
                                    }
                                    </tr>
                                )

                            }))
                        }
                        </tbody>
                    </table>
                </td>
            </tr>
        </tbody>
    </table>
    )
}

export default GuessTheWordInput;