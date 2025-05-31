export interface News {
    id: number,
    title: string,
    shorts: string,
    contents: string,
    thumbnails?: ImageProps[],
    gallery?: ImageProps[],
    file?: ImageProps[],
    createAt: string,
    updateAt: string,
}

export interface ImageProps {
    id: number,
    type: string,
    typeId: number,
    category: string,
    originName: string,
    saveName: string,
    size: number,
    createdAt: string,
}

const newsList: News[] = [
    {
        id: 1,
        title: "학과 뉴스",
        shorts: "학과 뉴스 단신",
        contents: "학과 뉴스 내용",
        thumbnails: [],
        gallery: [],
        file: [],
        createAt: "2024-08-31T22:02:40.537994",
        updateAt: "2024-08-31T22:02:40.537994",
    },
];
export default newsList;