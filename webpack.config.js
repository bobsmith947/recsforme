/*
 * Copyright 2018 Lucas Kitaev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

var path = require("path");
var faMin = require("font-awesome-minify-plugin");

module.exports = {
  entry: ["./src/web/page.js", "./src/web/bindings.js"],
  output: {
    filename: "bundle.js",
    path: path.resolve(__dirname, "web")
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: "babel-loader",
        options: {
          presets: ["env"]
        }
      },
      {
        test: /\.css$/,
        exclude: /node_modules/,
        use: [
          "style-loader",
          "css-loader",
          "postcss-loader"
        ]
      },
      {
        test: /\.css$/,
        include: /@fortawesome|fontAwesomeMinify/,
        use: [
          "style-loader",
          "css-loader"
        ]
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        include: /@fortawesome|fontAwesomeMinify/,
        loader: "file-loader"
      },
      {
        test: /\.(woff|woff2|eot|ttf|otf)$/,
        include: /@fortawesome|fontAwesomeMinify/,
        loader: "file-loader"
      }
    ]
  },
  plugins: [
    new faMin({
      globPattern: "**/*(*.jsp|*.jspf|*.java)",
      debug: true
    })
  ]
};
