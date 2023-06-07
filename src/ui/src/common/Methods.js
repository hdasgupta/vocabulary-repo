
export default class Methods {
    constructor() {

    }

    toProperCase (str) {
        return str[0].toUpperCase()+str.slice(1);
    }
}

export function range(startInclusive, endInclusive):ReadonlyArray<number> {
    return [...Array(endInclusive-startInclusive+1).keys()].map(i => i + startInclusive);
}

//
//function withFetch(Component, url, args={}, setData, setIsLoading, isLoading, initialFetchCriteria) {
//  return function WrappedComponent(props) {
//    useFetch(url, args, setData, setIsLoading, isLoading, initialFetchCriteria);
//    return <Component {...props} />;
//  }
//}
