export interface Graduation {
  year: number;
  file: ImageProps[];
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