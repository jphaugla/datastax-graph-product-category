var webpack = require('webpack');
var path = require('path');

var PROD_BUILD_DIR = path.resolve(__dirname, 'public/');
var APP_DIR = path.resolve(__dirname, '.')

var entryPoints = {
    index: './index.jsx',
}

var prod = {
    entry: entryPoints,
    output: {
        path: PROD_BUILD_DIR,
        filename: "[name].js"
    },
    module : {
        loaders : [
            {
                test : /\.jsx?/,
                exclude: /node_modules/,
                include : APP_DIR,
                loader : 'babel-loader'
            },
            {
                test: /\.scss$/,
                use: [
                    {
                        loader: "style-loader"
                    },
                    {
                        loader: "css-loader"
                    },
                    {
                        loader: "sass-loader"
                    }
                ]
            },
            {
                test: /\.(png)$/,
                loader: 'url-loader?limit=100000'
            },
            {
                test: /\.(svg|woff|woff2|ttf|eot)(\?.*$|$)/,
                use: [
                    {
                        loader: 'file-loader',
                        options: {}
                    }
                ]
            }
            // {test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: 'file-loader?mimetype=image/svg+xml'},
            // {test: /\.woff(\?v=\d+\.\d+\.\d+)?$/, loader: "file-loader?mimetype=application/font-woff"},
            // {test: /\.woff2(\?v=\d+\.\d+\.\d+)?$/, loader: "file-loader?mimetype=application/font-woff"},
            // {test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: "file-loader?mimetype=application/octet-stream"},
            // {test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: "file-loader"},
        ]
    }
};

module.exports = [prod];