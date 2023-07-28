import jsonServerProvider from "ra-data-json-server";
import { API_URL } from "../../constants";

const dataProvider = jsonServerProvider(API_URL);


const customDataProvider = {
    ...dataProvider,
    update: (resource: any, params: any) => {
        if (resource !== 'products') {
            // fallback to the default implementation
            return dataProvider.update(resource, params);
        }

        /**
         * For posts update only, convert uploaded image in base 64 and attach it to
         * the `picture` sent property, with `src` and `title` attributes.
         */
        
        // Freshly dropped pictures are File objects and must be converted to base64 strings
        const newPictures = params.data.pictures.filter(
            (p: any) => p.rawFile instanceof File
        );
        const formerPictures = params.data.pictures.filter(
            (p: any) => !(p.rawFile instanceof File)
        );



        return Promise.all(newPictures.map(convertFileToBase64))
            .then(transformedNewPictures =>
                dataProvider.update(resource, {
                    data:  {
                        ...params.data,
                        pictures: [
                            ...transformedNewPictures,
                            ...formerPictures,
                        ],
                    },
                    id:params.id,
                    previousData: params.previousData
                })
            );
    },

    create: (resource: any, params: any) => {
        if (resource !== 'products') {
            // fallback to the default implementation
            return dataProvider.create(resource, params);
        }

        /**
         * For posts update only, convert uploaded image in base 64 and attach it to
         * the `picture` sent property, with `src` and `title` attributes.
         */
        
        // Freshly dropped pictures are File objects and must be converted to base64 strings
        const newPictures = params.data.pictures.filter(
            (p: any) => p.rawFile instanceof File
        );
        const formerPictures = params.data.pictures.filter(
            (p: any) => !(p.rawFile instanceof File)
        );



        return Promise.all(newPictures.map(convertFileToBase64))
            .then(transformedNewPictures =>
                dataProvider.create(resource, {
                    data:  {
                        ...params.data,
                        pictures: [
                            ...transformedNewPictures,
                            ...formerPictures,
                        ],
                    },
                })
            );
    },


    
};

/**
 * Convert a `File` object returned by the upload input into a base 64 string.
 * That's not the most optimized way to store images in production, but it's
 * enough to illustrate the idea of data provider decoration.
 */
const convertFileToBase64 = (file: any) =>
    new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => resolve({src: reader.result, title: file.title});
        reader.onerror = reject;

        reader.readAsDataURL(file.rawFile);
});

export default customDataProvider;
