export interface Council {
  name: string;
  link: string;
  year: number;
  slogan: string;
  thumbnails: ImageProps[];
  people: ImageProps[];
  description: string;
}

export interface CouncilEvent {
  id: number;
  title: string;
  description: string;
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