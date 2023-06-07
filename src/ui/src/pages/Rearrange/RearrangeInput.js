import React, {useState} from 'react';
import { faBars, faArrowLeft} from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Button from 'react-bootstrap/Button';
import {range} from '../../common/Methods'
import {withPostFetch} from '../../fetches/FetchChangeOneLetter'
import '../../pages/Rearrange/RearrangeInput.css'

const RearrangeInput = (props)=> {
    const {onSelect, chars} = props
    const wordLength = chars.length;
    const [keys, setKeys] = useState(chars.map(char=>{
        return {'text':char, 'tab':1, 'type':'_letter', 'disabled': false}
    }))

    const del = {'icon':'arrowLeft', 'tab':1, 'type':'_delete', 'disabled':false};
    const submit = {'text':'Submit', 'tab':wordLength-1, 'type':'_submit', 'disabled':true}

    const [controlKeys, setControlKeys] = useState([del, submit])

    let [index, setIndex] = useState(0);
    let [validate, setValidate] = useState(false)
    const initChars = ()=>{return range(0, wordLength-1).map(i=>'')}
    let [word, setWord] = useState(initChars())

    console.log('chars = '+chars+', wordLength = '+wordLength+', onSelect = '+onSelect)

    const setSubmitDisabled = (valid) => {
        controlKeys[1].disabled = !valid;
        setValidate(valid);

    }
    
    const _letter = (text) => {
        if(index < wordLength) {
            word[index] = text;
            keys.forEach(key => {
                if(key.text===text) {
                    key.disabled = true;
                }
            })
            if((index + 1) == (wordLength-1)) {
                const remaining = keys.filter(key =>!key.disabled)[0].text;
                word[index + 1] = remaining
                keys.forEach(key => {
                    if(key.text===remaining) {
                        key.disabled = true;
                    }
                })
                index = index + 1
            }
            if((index + 1) >= wordLength) {
                withPostFetch(
                    '/api/games/rearrange/validate/'+props.id+'/'+word.join(''),
                    {},
                    setSubmitDisabled,
                    ()=>{return true}
                )
            }
            setIndex(index + 1)
        }
    }
    
    const _delete = () => {
        if(index > 0) {
            keys.forEach(key => {
                if(key.text===word[index - 1]) {
                    key.disabled = false;
                }
            })
            word[index - 1] = '';
            validate = false;
            submit.disabled = true;
            setIndex(index - 1);
        }
    }

    const _submit = () => {
        if(validate) {
            validate = false;
            keys.forEach(key =>key.disabled=false)
            onSelect(word.join(''))
            range(0, chars.length-1).forEach((i) => word[i] = '');
            setIndex(0)
        }
    }

    const actions = {_letter, _delete, _submit}

    return (
    <table>
        <tbody>
            <tr>
            {
                !!word?
                word.map((char, i)=> (
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
                            [
                                keys,
                                controlKeys
                            ].map((keys=>{
                                return (
                                    <tr>
                                    {
                                        keys.map((key)=>{
                                            return (
                                                <td colSpan={key.tab}>
                                                    <Button variant="primary" onClick={(e)=>actions[key.type](key.text)} disabled={key.disabled}>
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

export default RearrangeInput;