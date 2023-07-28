export default interface IPaginatedDTO {
  elementsPerPage: IProductData[],
  totalCount: number
}

export interface IProductData {
    id: Number
    name: string,
    categoryId: Number,
    pictures: IImage[],
    description: string
}


export interface IImage {
  src: string,
  title: string
}