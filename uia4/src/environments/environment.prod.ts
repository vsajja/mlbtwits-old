// `.env.json` is generated by the `npm run build` command
import * as env from './.env.json';

export const environment = {
  production: true,
  version: env.npm_package_version,
  // serverUrl: 'https://www.mlbtwits.com/api/v1',
  serverUrl: 'https://mlbtwits-app.herokuapp.com/api/v1',
  defaultLanguage: 'en-US',
  supportedLanguages: [
    'en-US',
    'fr-FR'
  ]
};
