export default interface ICategoryData {
    id: Number
    name:string,
    parentCategoryId:Number,
    subCategories?:Array<ICategoryData>
  }