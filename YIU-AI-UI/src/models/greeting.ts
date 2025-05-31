export interface Greeting {
    id: number;
    name: string;
    greeting: string;
    image: ImageProps[];
    autograph: ImageProps[];
    createdAt: string;
    updatedAt: string;
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


const greetingList: Greeting[] = [
  {
    id: 1,
    name: "이경재",
    greeting: "greeting",
    image: [],
    autograph: [],
    createdAt: "2024-08-31T22:02:40.537994",
    updatedAt: "2024-08-31T22:02:40.537994",
  },
];
export default greetingList;