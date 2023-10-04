import utils
import converter

def main():
    args = utils.parse_arguments()
    source_file = args.source_file
    target_file = args.target_file
    source_format = args.source_format
    target_format = args.target_format

    converter.convert_format(source_file, target_file, source_format, target_format)
    print(f"Conversion from {source_format} to {target_format} is complete.")

if __name__ == '__main__':
    main()