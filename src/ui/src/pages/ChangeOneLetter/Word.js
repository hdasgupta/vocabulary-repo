import React from 'react';

const Word = (props) => {
    return (
        <tr>
            {
                props.word.map((chr, index)=> {
                    if(index==props.changedIndex)
                        return (
                            <td class='changed-char-class'><center>{chr}</center></td>
                        );
                    else
                        return (
                            <td class='char-class'><center>{chr}</center></td>
                        );
                })
            }
            <td>
                <center>{props.computer?'🖥️':'🧑'}</center>
            </td>
        </tr>
    )
}

export default Word;