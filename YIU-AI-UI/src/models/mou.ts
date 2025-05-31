export interface Mou {
  id: number;
  name: string;
  image: ImageProps[];
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

const mouList: Mou[] = [
  {
    id: 1,
    name: "MOU",
    image: [],
    createdAt: "2024-08-31T22:02:40.537994",
    updatedAt: "2024-08-31T22:02:40.537994",
  },
];
export default mouList;