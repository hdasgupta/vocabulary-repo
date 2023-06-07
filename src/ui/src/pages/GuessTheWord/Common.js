const guessTypeNames = {
   'No Idea': 'light',
   'Doubt': 'warning',
   'Contains Surely' : 'success',
   'Not Contains Surely': 'danger',
}

const guessTypeLabels = Object.keys(guessTypeNames);

const guessTypeNamesVariant = {};

guessTypeLabels.map(guessType=>guessTypeNamesVariant[guessType.replace(/\s+/g, '')]=guessTypeNames[guessType])

const guessTypes = guessTypeLabels.map(guessType=>guessType.replace(/\s+/g, ''))

const guessTypeIndices = {};

guessTypes.forEach((guessType, index) => {
    guessTypeIndices[guessType]=index;
})

const nextGuessType = (guessType) => {
    const currentGuessTypeIndex = guessTypeIndices[guessType];
    const nextGuessTypeIndex = (currentGuessTypeIndex + 1) % guessTypes.length;

    return guessTypes[nextGuessTypeIndex];
}

const getVariant = (guessType) => {
    return guessTypeNamesVariant[guessType]
}

const getVariantByLabel = (label) => {
    return guessTypeNames[label]
}

const getAllLabels = () => {
    return guessTypeLabels;
}

const Common = ()=>{
    return {};
}

export {nextGuessType, getVariant, getVariantByLabel, getAllLabels};

export default Common;
